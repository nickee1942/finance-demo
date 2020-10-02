package com.nick.seller.service;

import com.nick.api.ProductRpc;
import com.nick.api.events.ProductStatusEvent;
import com.nick.entity.Product;
import com.nick.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductRpcService implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger LOG = LoggerFactory.getLogger(ProductRpcService.class);

    private static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_STATUS";

    @Autowired
    private ProductRpc productRpc;
    @Autowired
    private ProductCache productCache;

    public List<Product> findAll(){
        return productCache.readAllCache();
    }

    public Product findOne(String id){
        Product product = productCache.readCache(id);
        if(product==null){
            productCache.removeCache(id);
        }
        return product;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        List<Product> products = findAll();
//        products.forEach(product -> {
//            productCache.putCache(product);
//        });
    }

    @JmsListener(destination = MQ_DESTINATION)
    void updateCache(ProductStatusEvent event){
        LOG.info("receive event:{}",event);
        productCache.removeCache(event.getId());
        if(ProductStatus.IN_SELL.equals(event.getStatus())){
            productCache.readCache(event.getId());
        }

    }

//    @PostConstruct
//    public void init(){
//        findOne("T001");
//    }

}
