package com.nekobitlz.devfest_spb.data;

public enum Tracks {

    ANDROID("#ff460e"),
    FRONTEND("#0e6bff"),
    COMMON("#5501cd");

    public static final String ANDROID_NAME = "android";
    public static final String FRONTEND_NAME = "frontend";
    public static final String COMMON_NAME = "common";

    private String color;

    Tracks(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
