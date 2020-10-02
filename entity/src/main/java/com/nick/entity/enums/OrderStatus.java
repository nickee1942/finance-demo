package com.nick.entity.enums;


public enum OrderStatus {
    INIT("initialing"),
    PROCESS("processing"),
    SUCCESS("success"),
    FAIL("fail");

    private String desc;

    OrderStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
