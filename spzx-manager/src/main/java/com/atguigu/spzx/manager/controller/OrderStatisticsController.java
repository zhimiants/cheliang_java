package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.OrderStatisticsService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@Tag(name = "订单统计")
@RestController
@RequestMapping(value = "/admin/system/orderSta")
public class OrderStatisticsController {

    @Autowired
    OrderStatisticsService orderStatisticsService;

    @Operation(summary = "查询柱状图数据")
    @GetMapping("/getOrderSta")
    public Result<OrderStatisticsVo> GetOrderSta(OrderStatisticsDto orderStatisticsDto){
        //OrderStatisticsDto 查询时间 开始->结束
        OrderStatisticsVo orderStatisticsVo = orderStatisticsService.GetOrderSta(orderStatisticsDto); //map 有两个集合 dateList 和 countList
        return Result.ok(orderStatisticsVo);
    }
}
