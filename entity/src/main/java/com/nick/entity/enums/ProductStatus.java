package com.nick.entity.enums;


public enum ProductStatus {
    AUDITING("auth"),

    IN_SELL("in_sell"),

    LOCKED("locked"),

    FINISHED("finished");

    private String desc;

    ProductStatus(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
