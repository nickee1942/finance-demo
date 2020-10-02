package com.nick.seller.service;

import com.hazelcast.core.HazelcastInstance;
import com.nick.api.ProductRpc;
import com.nick.api.domain.ProductRpcReq;
import com.nick.entity.Product;
import com.nick.entity.enums.ProductStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
public class ProductCache {
    static final String CACHE_NAME = "nick_product";

    private static Logger LOG  = LoggerFactory.getLogger(ProductCache.class);

    @Autowired
    private ProductRpc productRpc;

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public List<Product> readAllCache(){
        Map map = hazelcastInstance.getMap(CACHE_NAME);
        List<Product> products = null;
        if(map.size()>0){
            products = new ArrayList<>();
            products.addAll(map.values());
        } else {
            products = findAll();
        }
        return products;
    }

    public List<Product> findAll(){
        ProductRpcReq req = new ProductRpcReq();
        List<String> status = new ArrayList<>();
        status.add(ProductStatus.IN_SELL.name());

        req.setStatusList(status);
        LOG.info("search all RPC products,request:{}",req);
        List<Product> result = productRpc.query(req);
        LOG.info("search all RPC products,result:{}",result);
        return result;
    }

    @Cacheable(cacheNames = CACHE_NAME)
    public Product readCache(String id){
        LOG.info("search RPC product,request:{}",id);
        Product result = productRpc.findOne(id);
        LOG.info("search RPC product,result:{}",result);
        return result;
    }

    @CachePut(cacheNames = CACHE_NAME,key = "#product.id")
    public Product putCache(Product product){
        return product;
    }

    @CacheEvict(cacheNames = CACHE_NAME)
    public void removeCache(String id){

    }
}
