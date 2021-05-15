package com.example.finapp.enums;

public enum ApplicationEnums {

    INCOME("Income"),
    EXCHANGE("Exchange");
    private String code;

    ApplicationEnums(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
