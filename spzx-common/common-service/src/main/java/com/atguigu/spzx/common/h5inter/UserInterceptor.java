package com.atguigu.spzx.common.h5inter;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Date:2024/1/18
 * Author:陈浩微
 * Description:在微服务前添加拦截器,先经过全局过滤器,再经过拦截器,最后访问目标接口
 * 拦截器的作用:获取请求的token,从redis中查询用户信息,把信息存入ThreadLocal中,
 * 下次使用就不用去redis中,而是直接从ThreadLocal中获取
 *
 */

//@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求头中的token
        String token = request.getHeader("token");
        String key = "user:login:" + token;
        //从redis中根据key获取json字符串
        String jsonString = redisTemplate.opsForValue().get(key);
        //将json字符串转成用户信息对象
        UserInfo userInfo = JSON.parseObject(jsonString, UserInfo.class);
        //在ThreadLocal中设置数据,提前来个非空判断
        if(userInfo!= null){
            ThreadLocalUtil.setH5(userInfo);
        }

        //设置完放行请求
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //请求处理完成之后,该线程会被返回线程池中,需要清除共享数据
        ThreadLocalUtil.removeH5();
    }
}
