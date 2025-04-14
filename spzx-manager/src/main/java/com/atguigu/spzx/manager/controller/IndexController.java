package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
//@CrossOrigin
public class IndexController {


    //自动装配service
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;


    //登陆
    //发送请求 LoginDto 响应回来是一个令牌 LoginVo token
    @Operation(summary = "登陆接口")
    @PostMapping(value = "/login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        //返回data:token
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }

    //获取验证码
    @Operation(summary = "获取验证码")
    @GetMapping("/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }
    //根据请求头的token获取用户信息
    @Operation(summary = "根据请求头的token获取用户信息")
    @GetMapping(value = "/getUserInfo")
    public Result getUserInfo(@RequestHeader(name = "token") String token){
        SysUser sysUser = sysUserService.getUserInfo(token);
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);

    }
    @Operation(summary = "根据请求头的token,清除redis中的用户数据")
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader("token") String token){
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
