package com.atguigu.spzx.util;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;

public class ThreadLocalUtil {


    //任意一个线程中，使用的是相同的threadLocal
    private final static ThreadLocal<SysUser> threadLocal = new ThreadLocal(); //后台系统
    private final static ThreadLocal<UserInfo> threadLocalH5 = new ThreadLocal();//前台页面



    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }
    public static void setH5(UserInfo userInfo){
        threadLocalH5.set(userInfo);
    }
    public static SysUser get(){
        return threadLocal.get();
    }

    public static UserInfo getH5(){
        return threadLocalH5.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
    public static void removeH5() {
        threadLocalH5.remove();
    }
}
