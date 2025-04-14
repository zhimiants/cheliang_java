package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface OrderStatisticsMapper {
    // 查询指定日期产生的订单数据
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);

    void add(OrderStatistics orderStatistics);
}
