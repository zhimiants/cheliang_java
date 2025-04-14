package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exp.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public LoginVo login(LoginDto loginDto) {
        //校验验证码
        String captcha = loginDto.getCaptcha();//输入的验证码
        String codeKey = loginDto.getCodeKey();// uuid那个key

        //redis中的数据以uuid为key,4位数为value
        String redisValue = redisTemplate.opsForValue().get(codeKey);
        //
        if (StringUtils.isEmpty(redisValue)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_TIMEOUT);
        }
        if (!redisValue.equals(captcha)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //验证码校验通过，从Redis中删除
        redisTemplate.delete(codeKey);


        String userName = loginDto.getUserName();
        String password = loginDto.getPassword();

        //查看用户名和密码是否为空
        if (userName == null){
            //throw new RuntimeException("用户名为空");
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_NULL);
        }
        if (password == null){
            //throw new RuntimeException("密码为空");
            throw new GuiguException(ResultCodeEnum.PASS_WORD_IS_NULL);
        }

        //根据username用户名查询用户，如果不存在则抛出异常
        SysUser sysUser = sysUserMapper.selectByUsername(userName);

        if (sysUser == null){
            //throw new RuntimeException("用户名不存在");
            throw new GuiguException(ResultCodeEnum.USER_NAME_NOT_EXIST);
        }
        //2、判断密码是否正确。注意：数据库中密码是md5加密的。MD5 加密是不可逆的,也就是说,从密文无法推导出原始数据。
        //MD5是一种哈希算法，将任意长度的数据输入，经过计算后输出为固定长度的唯一哈希值，通常为32位16进制数。
        String md5_password = sysUser.getPassword();
        if (!DigestUtil.md5Hex(password).equals(md5_password)){
            //throw new RuntimeException("密码错误");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }


        if (sysUser.getStatus()==0){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        //3、使用uuid生成一个唯一token（"user:login:" + token 作为redis中的key）
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        //4、将当前用户信息转成json字符串存储到redis中，暂存时间30分钟
        //key 是tokean value 是用户信息
        String userMessage = JSON.toJSONString(sysUser);

        //7、存储到redis中，过期时间30分钟
        redisTemplate.opsForValue().set(token,userMessage,30, TimeUnit.MINUTES);

        //8、将token封装成LoginVo对象，返回即可
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {

        String key = token;
        //把获取的json字符串变成对象
        String jsonString = redisTemplate.opsForValue().get(key);
        SysUser sysUser = JSON.parseObject(jsonString, SysUser.class);
        //要的主要是头像和name
        return sysUser;
    }

    @Override
    public void logout(String token) {
        String key = token;
        redisTemplate.delete(key);
    }
    //---------------------------用户模块---------------
    @Override
    public PageInfo findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        //设置分页参数
        PageHelper.startPage(pageNum,pageSize);
        //调用sql,执行查询语句
        List<SysUser> list = sysUserMapper.findAll(sysUserDto);

        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void addUser(SysUser sysUser) {
        //1.各种非空校验
        String userName = sysUser.getUserName();
        String name = sysUser.getName();
        String password = sysUser.getPassword();
        String phone = sysUser.getPhone();
        if (StringUtils.isEmpty(userName)){
            throw new GuiguException(222,"用户名不能为空");
        }
        if (StringUtils.isEmpty(password)){
            throw new GuiguException(222,"密码不能为空");
        }
        if (StringUtils.isEmpty(name)){
            throw new GuiguException(222,"真实姓名不能为空");
        }
        if (StringUtils.isEmpty(phone)){
            throw new GuiguException(222,"电话不能为空");
        }
        //2.判断添加的用户 是否重复 主要是userName和phone不能重复
        SysUser sysUserFromDB = sysUserMapper.selectByUsername(userName);
        if (sysUserFromDB != null){
            throw new GuiguException(266,"该用户名已占用");
        }
        //将密码加密
        String md5Password = DigestUtil.md5Hex(password);
        sysUser.setPassword(md5Password);

        //设置账号的默认状态
        sysUser.setStatus(1);
        //判断电话是否重复
        Integer countPhone = sysUserMapper.selectPhone(phone);

        if (countPhone > 0){
            throw new GuiguException(269,"该电话号码已被注册");
        }
        //执行添加语句
        sysUserMapper.addUser(sysUser);

    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        //1.必要的非空校验 拿userName举例
        String userName = sysUser.getUserName();
        if (StringUtils.isEmpty(userName)){
            throw new GuiguException(220,"用户名不能为空");
        }
        String phone = sysUser.getPhone();
        String name = sysUser.getName();
        Long id = sysUser.getId();


        //判断点击修改的id和传入的是不是同一个账号,不是的话要进行重复校验
        SysUser sysUserByDB = sysUserMapper.selectUserById(id);
        if (!sysUserByDB.getUserName().equals(userName)){
            if (sysUserMapper.selectByUsername(userName) != null){
                throw new GuiguException(233,"该用户名已被占用");
            }
        }
        //电话也不可重复
        if (!sysUserByDB.getPhone().equals(phone)){
            if (sysUserMapper.selectPhone(phone) > 0){
                throw new GuiguException(234,"该电话号码已被占用");
            }
        }

        sysUserMapper.updateById(sysUser);
    }

    @Override
    public void deleteSysUser(Long id) {
        sysUserMapper.deleteSysUser(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            deleteSysUser(id);
        }
    }

}

