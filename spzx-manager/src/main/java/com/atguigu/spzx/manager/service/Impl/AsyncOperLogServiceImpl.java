package com.atguigu.spzx.manager.service.Impl;

import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.manager.mapper.SysOperLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {


    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Async
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insertOperLog(sysOperLog);
    }
}
