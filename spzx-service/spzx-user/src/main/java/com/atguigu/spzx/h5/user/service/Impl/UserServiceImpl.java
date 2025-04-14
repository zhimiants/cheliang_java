package com.atguigu.spzx.h5.user.service.Impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exp.GuiguException;
import com.atguigu.spzx.h5.user.mapper.UserMapper;
import com.atguigu.spzx.h5.user.service.UserService;
import com.atguigu.spzx.h5.user.utils.HttpUtils;
import com.atguigu.spzx.h5.user.utils.MessageProperties;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.util.ThreadLocalUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    MessageProperties messageProperties;
    @Override
    public void sendCode(String phone) {
        //1.检查手机号是否存在有效期5分钟内的验证码
        String key = "user:code:" + phone;
        String code = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(code)){
            //redis中找到value:code 说明还在有效期
            return;
        }
        //2.调用阿里云发送短信的接口
        //2.1 获取随机的4位验证码
        code = this.getRandomCode();
        //2.2 //发送短信
        this.sendCodeToAliyun(phone,code);

        //将验证码暂存redis 5分钟
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);
    }


    private void sendCodeToAliyun(String phone, String code) {

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + messageProperties.getAppcode());
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phone);
        querys.put("param", "**code**:"+code+",**minute**:5");

//smsSignId（短信前缀）和templateId（短信模板），可登录国阳云控制台自助申请。参考文档：http://help.guoyangyun.com/Problem/Qm.html

        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从\r\n\t    \t* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java\r\n\t    \t* 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(messageProperties.getHost(), messageProperties.getPath(), messageProperties.getMethod(), headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandomCode() {
       long code =  (long)((new Random().nextDouble()) * 10000); //4位
        if (code < 1000){
            code = code + 1000;
        }
        return code+"";
    }



    @Override
    public void userRegistry(UserRegisterDto userRegisterDto) {
        //非空校验
        String username = userRegisterDto.getUsername(); //手机号
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
        if(StringUtils.isEmpty(username) ||
            StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(nickName) ||
                    StringUtils.isEmpty(code)
        ){
            throw new GuiguException(208,"注册信息不能为空");
        }

        //验证短信验证码
        String key = "user:code:" + username;

        String codeByRedis = redisTemplate.opsForValue().get(key);
        if(StringUtils.isEmpty(codeByRedis)){
            throw new GuiguException(255,"验证码已过期");
        }
        if(!code.equals(codeByRedis)){
            throw new GuiguException(255,"验证码不正确");
        }
        //验证手机号是否存在
        UserInfo userInfo = userMapper.selectByUsername(username);
        if (userInfo != null){
            throw new GuiguException(255,"用户名已存在");
        }

        //如果userinfo为null,设置status默认值
        userInfo = new UserInfo();
        userInfo.setStatus(1); //启用
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)));

        userMapper.insert(userInfo);

        //注册成功从redis删除验证码
        redisTemplate.delete(key);

    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        //1.非空校验
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if (StringUtils.isEmpty(username)){
            throw new GuiguException(201,"用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new GuiguException(201,"密码不能为空");
        }
        //2.校验账号的登陆状态 根据username查询
        UserInfo userInfo = userMapper.selectByUsername(username);
        Integer status = userInfo.getStatus();
        if(userInfo == null){
            throw new GuiguException(201,"该用户不存在");
        }
        if (status == 0){
            throw new GuiguException(201,"该账号已被禁用");
        }
        //3.校验密码
        String passwordFromDB = userInfo.getPassword();
        if(!passwordFromDB.equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            throw new GuiguException(201,"密码错误");
        }
        //4.修改登陆时间 和ip
        String ip = userLoginDto.getIp();
        userInfo.setLastLoginIp(ip);
        userInfo.setLastLoginTime(new Date());

        //修改user_info表
        userMapper.updateLoginInfo(userInfo);


        //将用户信息装成jsonString 字符串
        String jsonString = JSON.toJSONString(userInfo);
        String token = UUID.randomUUID().toString().replace("-", "");
        String key = "user:login:" + token;
        //将用户信息放入redis中
        redisTemplate.opsForValue().set(key,jsonString,30,TimeUnit.MINUTES);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //查询redis
//        String key = "user:login:" + token;
//        String jsonString = redisTemplate.opsForValue().get(key);
//
//        //将json字符串变成用户 userinfo 对象
//        //因为只有userinfo 和 redis数据对应
//        UserInfo userInfo = JSON.parseObject(jsonString, UserInfo.class);


        //上面是没有使用拦截器的情况
        UserInfo userInfo = ThreadLocalUtil.getH5();
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setNickName(userInfo.getNickName());
        userInfoVo.setAvatar(userInfo.getAvatar());
        return userInfoVo;
    }

    @Override
    public List<UserAddress> findUserAddressList() {
        UserInfo userInfo = ThreadLocalUtil.getH5();
        Long userId = userInfo.getId();
        List<UserAddress> list = userMapper.selectAddressListByUserId(userId);
        return list;
    }

    @Override
    public void removeAddressById(Long addressId) {
        userMapper.removeAddressById(addressId);
    }

    @Override
    public List<Region> findByParentCode(Long parentCode) {
        List<Region> list = userMapper.findByParentCode(parentCode);
        return list;
    }

    @Override
    public void addAddress(UserAddress userAddress) {
        Long userId = ThreadLocalUtil.getH5().getId();
        //新增地址 又有默认地址属性1
        if (userAddress.getIsDefault() == 1){
            //修改该用户其他地址为0
            userMapper.updateAddressDefaultToZero(userId);
        }

        userAddress.setUserId(userId);
        //省市区
        String provinceCode = userAddress.getProvinceCode();
        String cityCode = userAddress.getCityCode();
        String districtCode = userAddress.getDistrictCode();

        //根据省市区查询完整地址的前缀
        String pcd = userMapper.getPcdName(provinceCode,cityCode,districtCode);

        String address = userAddress.getAddress();
        String fullAddress = pcd + address;
        userAddress.setFullAddress(fullAddress);

        userMapper.addAddress(userAddress);



    }

}
