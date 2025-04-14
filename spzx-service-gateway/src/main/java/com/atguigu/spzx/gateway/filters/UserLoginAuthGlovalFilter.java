package com.atguigu.spzx.gateway.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Component
//gateway网关的全局过滤器
public class UserLoginAuthGlovalFilter implements GlobalFilter, Ordered {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1.获取请求头的token
        HttpHeaders headers = exchange.getRequest().getHeaders();
        List<String> tokenList = headers.get("token");
        String token = "";
        if(tokenList!= null && tokenList.size() > 0){
            token = tokenList.get(0);
        }
        System.out.println("token = " + token);

        //2.根据token去redis查询用户信息
        String key = "user:login:" + token;
        String userInfoString = redisTemplate.opsForValue().get(key);

        //3.获取请求路径
        URI uri = exchange.getRequest().getURI();
        String path = uri.getPath();

        //4.判断该路径是否需要登陆校验,如果需要校验登陆,用户未登录则拦截请求,
        // 返回208,message 用户未登录

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/api/**/auth/**", path);
        if (match){
            //说明访问的接口需要登陆校验
            if (StringUtils.isEmpty(userInfoString)){
                //redis查不到用户信息,该用户未登录
                //给用户响应数据,如果208自动跳到登陆页面
                this.out(exchange.getResponse());
            }
        }
        //如果不需要校验登陆,或者已经登陆成功了,将会话时长延长30
        if (!StringUtils.isEmpty(userInfoString)){
            redisTemplate.expire(key,30, TimeUnit.MINUTES);
        }

        //放行请求
        Mono<Void> filter = chain.filter(exchange);
        return filter;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response){
        Result result = Result.build(null,208,"用户未登录");
        //转json字符串
        String toJSONString = JSONObject.toJSONString(result);
        byte[] bytes = toJSONString.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        //指定编码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }
}
