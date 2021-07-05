package com.gwang.test.testng.pricing.common;

/**
 * 定价依赖参数枚举
 */
public enum PriceParamEnum {

    DISTANCE("distance", "运单距离"),
    GRAB_TIME("grabTime", "骑手抢单时间"),
    CATEGORY("categoryRule", "商品品类"),
    ;

    PriceParamEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
