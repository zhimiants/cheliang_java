package com.atguigu.spzx.manager.service.Impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderStatisticsService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    @Autowired
    OrderStatisticsMapper orderStatisticsMapper;

    /**
     * 查询柱状图数据
     * @param orderStatisticsDto
     * @return
     */
    @Override
    public OrderStatisticsVo GetOrderSta(OrderStatisticsDto orderStatisticsDto) {
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);
        List<String> dateList = orderStatisticsList.stream().
                map(orderStatistics -> DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))
                .collect(Collectors.toList());
        List<BigDecimal> amountList= orderStatisticsList.stream().map(OrderStatistics::getTotalAmount).collect(Collectors.toList());
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);
        return orderStatisticsVo;
    }
}
