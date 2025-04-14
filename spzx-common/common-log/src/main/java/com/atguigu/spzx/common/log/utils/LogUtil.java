package com.atguigu.spzx.common.log.utils;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import com.atguigu.spzx.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

public class LogUtil {
    
    //操作执行之前调用
    public static void beforeHandleLog(Log sysLog,
                                       ProceedingJoinPoint joinPoint,
                                       SysOperLog sysOperLog) {

        //1、设置操作模块标题tile,从sysLog中获取
        sysOperLog.setTitle(sysLog.title());
        
        //2、设置method方法名称,包名.类名.方法名()，例如：xxx.controller.SysRoleController.save()
        //joinPoint中getSignature方法强转成MethodSignature对象，
        //再获取到Method，再获取DeclaringClass全类名，再getMethod获取方法名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature() ;
        Method method = methodSignature.getMethod();
        
        String className = method.getDeclaringClass().getName();//包名.类名
        String methodName = method.getName();//方法名称

        sysOperLog.setMethod(className + "." + methodName + "()");
                
        //3、设置请求方式，赋值给RequestMethod
        //从RequestContextHolder获取RequestAttributes强转成ServletRequestAttributes，
        //再调用getRequest得到request请求，再获取getMethod获取请求方式
        ServletRequestAttributes requestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        HttpServletRequest request = requestAttributes.getRequest();
        sysOperLog.setRequestMethod(request.getMethod());

        //4、设置操作类别（0其它 1后台用户 2手机端用户），赋值给OperatorType
        sysOperLog.setOperatorType(sysLog.operatorType().name());
        //5、获取当前登录用户username，赋值给OperName操作人员
		sysOperLog.setOperName(ThreadLocalUtil.get().getUserName());
        //6、当前请求的URL（request.getRequestURI()）赋值给OperUrl
        sysOperLog.setOperUrl(request.getRequestURI());
        //7、获取远程主机IP地址（request.getRemoteAddr()）
        sysOperLog.setOperIp(request.getRemoteAddr());
        
        //8、判断Log注解中isSaveRequestData如果为true，则需要保存请求参数
        if(sysLog.isSaveRequestData()) {
            String requestMethod = sysOperLog.getRequestMethod();
            //如果是PUT或POST类型的请求，则获取方法参数；其它类型的参数都在url上
            if (HttpMethod.PUT.name().equals(requestMethod)||HttpMethod.POST.name().equals(requestMethod)) {
                //获取带参方法的参数
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }
    }

    //操作执行之后调用（proceed表示目标方法的返回值）
    public static void afterHandlLog(Log sysLog, 
                                     Object proceed,
                                     SysOperLog sysOperLog, 
                                     int status ,
                                     String errorMsg) {
        //1、判断Log注解中isSaveResponseData为true，表示需要保存目标接口的返回值。将目标方法返回值转成json赋值给JsonResult
        if(sysLog.isSaveResponseData()) {
            sysOperLog.setJsonResult(JSON.toJSONString(proceed));
        }
        //2、设置操作状态（0正常 1异常）
        sysOperLog.setStatus(status);
        //3、如果有异常，设置ErrorMsg
        sysOperLog.setErrorMsg(errorMsg);
    }

}