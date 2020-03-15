package com.zzx.sharding.enums;

/**
 * 统一返回状态码枚举
 */
public enum ResponseEnum {

    SUCCESS(0, "成功"),
    FAIL(1, "失败");

    private int code;

    private String name;

    ResponseEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
