package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {

    //自动装配redis
    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public ValidateCodeVo generateValidateCode() {
        //生成验证码图片 宽 高 字符数 干扰线数
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 50, 4, 20);
        //获取验证码
        String code = circleCaptcha.getCode();
        //获取base64编码图
        String imageBase64 = circleCaptcha.getImageBase64();

        //生成uuid,作为key
        String key = UUID.randomUUID().toString().replaceAll("-", "");
        //uuid为key,4位验证码为值 放在redis 5分钟过期
        redisTemplate.opsForValue().set(key,code,5, TimeUnit.MINUTES);

        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(key);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        return validateCodeVo;
    }
}
