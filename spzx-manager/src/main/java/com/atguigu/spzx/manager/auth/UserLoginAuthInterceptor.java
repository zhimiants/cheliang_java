package com.atguigu.spzx.manager.auth;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;


@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //预见请求
        String method = request.getMethod();
        System.out.println("请求 = " + method);
        if (method.equals("OPTIONS")){
            return true;
        }
        //token没有
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)){
            response208(response);
            return false;
        }
        //有token看也没过期,有查询到,刷新时常
        String key = token;
        String jsonString = redisTemplate.opsForValue().get(key);
        if (StringUtils.isEmpty(jsonString)){
            response208(response);
            return false;
        }
        SysUser sysUser = JSON.parseObject(jsonString, SysUser.class);
        //后续可以优化获取用户信息的接口  直接从线程副本中拿
        ThreadLocalUtil.set(sysUser);

        //刷新有效时间
        redisTemplate.expire(key,30, TimeUnit.MINUTES);
        //放行
        return true;
    }
    private void response208(HttpServletResponse response){
        PrintWriter writer = null;

        try {
            Result result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);

            String jsonString = JSON.toJSONString(result);
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            writer = response.getWriter();
            writer.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(writer!=null){
                writer.close();
            }
        }


    }

    //这个方法是在请求处理完成之后，且在响应返回给用户之前执行的。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除threadLocal中的数据
        ThreadLocalUtil.remove();
    }
}
