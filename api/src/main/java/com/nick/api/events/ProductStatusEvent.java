package com.nick.api.events;

import com.nick.entity.enums.ProductStatus;

import java.io.Serializable;

public class ProductStatusEvent implements Serializable {

    private String id;
    private ProductStatus status;

    public ProductStatusEvent(String id, ProductStatus status) {
        this.id = id;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ProductStatusEvent{" +
                "id='" + id + '\'' +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }
}
