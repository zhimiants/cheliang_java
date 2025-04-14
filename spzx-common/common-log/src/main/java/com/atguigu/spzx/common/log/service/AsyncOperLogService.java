package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

/**
 * Date:2024/4/13
 * Author:陈浩微
 * Description:
 *
 * 保存日志信息  接口  在manager中实现
 */
public interface AsyncOperLogService {
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}
