package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;

import java.util.Map;


public interface OrderStatisticsService {
    /**
     * 查询柱状图数据
     * @param orderStatisticsDto
     * @return
     */
    OrderStatisticsVo GetOrderSta(OrderStatisticsDto orderStatisticsDto);

}
