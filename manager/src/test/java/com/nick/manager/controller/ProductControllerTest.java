package com.nick.manager.controller;

import com.nick.entity.Product;
import com.nick.entity.enums.ProductStatus;
import com.nick.util.RestUtil;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductControllerTest {

    private static RestTemplate rest = new RestTemplate();

    @Value("http://localhost:${local.server.port}/manager")
    private String baseUrl;


    private static List<Product> normals = new ArrayList<>();

    private static List<Product> exceptions = new ArrayList<>();

    @BeforeClass
    public static void init(){
        Product p1 = new Product("T001", "pro1", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(3.42));
        Product p2 = new Product("T002", "pro2", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(0), BigDecimal.valueOf(3.28));
        Product p3 = new Product("T003", "pro3", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(10), BigDecimal.valueOf(3.86));
        normals.add(p1);
        normals.add(p2);
        normals.add(p3);

        Product e1 = new Product(null, "null id", ProductStatus.AUDITING.name(),
                BigDecimal.valueOf(10), BigDecimal.valueOf(1), BigDecimal.valueOf(2.34));
        Product e2 = new Product("E002", "wrong rate range", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1), BigDecimal.valueOf(31));
        Product e3 = new Product("E003", "value is not an integer", ProductStatus.AUDITING.name(),
                BigDecimal.ZERO, BigDecimal.valueOf(1.01), BigDecimal.valueOf(3.44));

        exceptions.add(e1);
//        exceptions.add(e2);
//        exceptions.add(e3);

        ResponseErrorHandler errorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {

            }
        };
        rest.setErrorHandler(errorHandler);
    }


    @Test
    public void create(){
        normals.forEach(product -> {
            Product result = RestUtil.postJSON(rest,baseUrl+"/products",product,Product.class);
            Assert.notNull(result.getCreateAt(),"insert failure");
        });

    }

    @Test
    public void createException(){
        exceptions.forEach(product -> {
            Map<String,String> result = RestUtil.postJSON(rest,baseUrl+"/products",product,HashMap.class);
            //System.out.println(result);
            Assert.isTrue(result.get("message").equals(product.getName()),"insert success");
        });
    }

    @Test
    public void findOne(){
        normals.forEach(product -> {
            Product result = rest.getForObject(baseUrl+"/products/"+product.getId(),Product.class);
            Assert.isTrue(result.getId().equals(product.getId()),"search fail");
        });
        exceptions.forEach(product -> {
            Product result = rest.getForObject(baseUrl+"/products/"+product.getId(),Product.class);
            Assert.isNull(result,"search fail");
        });
    }

    public void query(){
        String[] ids = {"T001","T002","T003"};

        normals.forEach(product -> {
            rest.getForObject(baseUrl+"/products",Page.class);
        });
    }

}
