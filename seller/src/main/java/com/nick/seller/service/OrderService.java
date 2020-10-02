package com.nick.seller.service;

import com.nick.entity.Order;
import com.nick.entity.Product;
import com.nick.entity.enums.OrderStatus;
import com.nick.entity.enums.OrderType;
import com.nick.seller.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;


@Service
public class OrderService {

    private static Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRpcService productRpcService;

    public Order apply(Order order){

        checkOrder(order);
        completeOrder(order);
        order = orderRepository.saveAndFlush(order);
        return order;
    }

    private void completeOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString().replaceAll("-",""));
        order.setOrderType(OrderType.APPLY.name());
        order.setOrderStatus(OrderStatus.SUCCESS.name());
        order.setUpdateAt(new Date());
    }

    private void checkOrder(Order order) {
        Assert.notNull(order.getOuterOrderId(),"need outer id");
        Assert.notNull(order.getChanId(),"need channel id");
        Assert.notNull(order.getChanUserId(),"need user id");
        Assert.notNull(order.getProductId(),"need product id");
        Assert.notNull(order.getAmount(),"need amount");
        Assert.notNull(order.getCreateAt(),"need order time");
        Product product = productRpcService.findOne(order.getProductId());
        Assert.notNull(product,"product does not exist");
    }
}
