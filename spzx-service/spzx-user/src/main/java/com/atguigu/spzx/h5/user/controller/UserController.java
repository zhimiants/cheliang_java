package com.atguigu.spzx.h5.user.controller;

import com.atguigu.spzx.h5.user.service.UserService;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    //短信验证码接口
    @GetMapping("sms/sendCode/{phone}")
    public Result sendCode(@PathVariable String phone){
        userService.sendCode(phone);
        return Result.ok(null);
    }
    //注册
    @PostMapping("userInfo/register")
    public Result userRegistry(@RequestBody UserRegisterDto userRegisterDto){
        userService.userRegistry(userRegisterDto);
        return Result.ok(null);
    }
    //登陆
    @PostMapping("/userInfo/login")
    public Result userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request){//账号和密码
       //获取ip地址
        String ip = request.getRemoteHost();
        userLoginDto.setIp(ip);
        String token = userService.login(userLoginDto);
        //登陆成功给前端返回token
        return Result.ok(token);
    }
    //根据请求头token查询redis中的用户信息,返回头像和昵称
    @GetMapping("userInfo/auth/getCurrentUserInfo")
    public Result getCurrentUserInfo(@RequestHeader("token") String token){
        UserInfoVo userInfoVo = userService.getCurrentUserInfo(token);
        return Result.ok(userInfoVo);
    }
    @Operation(summary = "根据用户id查询地址")
    @GetMapping("/userAddress/auth/findUserAddressList")
    public Result findUserAddressList(){
        List<UserAddress> list = userService.findUserAddressList();
        return Result.ok(list);
    }

    @Operation(summary = "删除地址")
    @DeleteMapping("/userAddress/auth/removeById/{addressId}")
    public Result removeById(@PathVariable Long addressId){
        userService.removeAddressById(addressId);
        return Result.ok(null);

    }

    @Operation(summary = "查询地址省市区")
    @GetMapping("/region/findByParentCode/{parentCode}")
    public Result findByParentCode(@PathVariable Long parentCode){
        List<Region> list = userService.findByParentCode(parentCode);
        return Result.ok(list);

    }

    @Operation(summary = "新增地址")
    @PostMapping("/userAddress/auth/save")
    public Result addAddress(@RequestBody UserAddress userAddress){
        userService.addAddress(userAddress);
        return Result.ok(null);

    }


}
