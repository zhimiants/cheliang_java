package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class OrderStaTask {

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    OrderStatisticsMapper orderStatisticsMapper;


    //秒 分 时 日 月 星期
    //*   *   *    *   *    ?
    @Scheduled(cron = "0 0 2 * * ?" ) // 每月每日每小时每分钟的第5秒钟
    public void timeTask() {

        //查询前一天的日期
        String createTime = DateUtil.offsetDay(new Date(),-1).toString("yyyy-MM-dd");
        //查询前一天的订单信息
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);

        if (orderStatistics!=null){
            orderStatisticsMapper.add(orderStatistics);
        }


        System.out.println(new Date());
    }

}
