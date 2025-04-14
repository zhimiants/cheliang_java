package com.atguigu.spzx.model.entity.order;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderStatistics extends BaseEntity {

    private Date orderDate; //订单时间
    private BigDecimal totalAmount; //总金额
    private Integer totalNum; //总数
    
}