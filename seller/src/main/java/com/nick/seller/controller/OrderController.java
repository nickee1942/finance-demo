package com.nick.seller.controller;

import com.nick.entity.Order;
import com.nick.seller.params.OrderParam;
import com.nick.seller.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/order")
public class OrderController {

    private static Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;


    @PostMapping("/apply")
    public Order apply(@RequestHeader String authId,@RequestHeader String sign, @RequestBody OrderParam param){
        LOG.info("apply request:{}",param);
        Order order = new Order();
        BeanUtils.copyProperties(param,order);
        order = orderService.apply(order);
        LOG.info("result:{}",order);
        return order;
    }

}
