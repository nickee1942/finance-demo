package com.nick.entity.enums;


public enum OrderType {
    APPLY("apply"),
    REDEEM("redeem");

    private String desc;

    OrderType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
