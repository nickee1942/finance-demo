package com.nick.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.nick.api.domain.ProductRpcReq;
import com.nick.entity.Product;

import java.util.List;

/**
 * 产品相关的rpc服务
 * @author TiHom
 * create at 2018/8/3 0003.
 */

@JsonRpcService("rpc/products") //这里不能以/开始例如 /products这是错误的
public interface ProductRpc {

    /**
     * Search multi products
     * @param req
     * @return
     */
    List<Product> query(ProductRpcReq req);

    /**
     * search single product
     * @param id
     * @return
     */
    Product findOne(String id);
}
