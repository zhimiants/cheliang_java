package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Date:2024/4/13
 * Author:陈浩微
 * Description:
 *
 * 定义切面类  方法是切入点  通知是对方法的增强
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    private AsyncOperLogService asyncOperLogService;


    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog){
//        String title = sysLog.title();  //操作的标题
//        int type = sysLog.businessType(); //操作的类型
//        System.out.println("title = " + title);
//        System.out.println("type = " + type);
        SysOperLog sysOperLog = new SysOperLog();
        //目标方法执行之前
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);


        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            //System.out.println("操作完完毕...");
            //目标方法执行之后
        LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            //遇到异常
            e.printStackTrace(); //打印错误信息，防止抛出异常无法执行后续操作
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 1 , e.getMessage()) ;
        }
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;

    }
}
