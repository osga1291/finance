package com.finance.models;

public enum AccountClass {
    ASSET("Asset"),
    LIABLILITY("Liability");

    private final String value;

    AccountClass(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}