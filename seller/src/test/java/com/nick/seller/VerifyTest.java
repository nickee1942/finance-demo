package com.nick.seller;

import com.nick.seller.service.VerifyService;
import com.nick.seller.repositories.OrderRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //使用SpringBoot环境随机端口
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VerifyTest {

    @Autowired
    private VerifyService verifyService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderRepository backupOrderRepoistory;

    @Test
    public void makeVerificationTest(){
        Date day = new GregorianCalendar(2020,0,1).getTime();
        File file = verifyService.makeVerificationFile("111",day);
        System.out.println(file.getAbsolutePath());
    }

    @Test
    public void saveVerificationOrders(){
        Date day = new GregorianCalendar(2020,0,1).getTime();
        verifyService.saveChanOrders("111",day);
    }

    @Test
    public void verifyTest(){
        Date day = new GregorianCalendar(2020,0,1).getTime();
        System.out.println(String.join(";", verifyService.verifyOrder("111", day)));
    }

    @Test
    public void queryOrder(){
        System.out.println(orderRepository.findAll());
        System.out.println(backupOrderRepoistory.findAll());
    }

}
