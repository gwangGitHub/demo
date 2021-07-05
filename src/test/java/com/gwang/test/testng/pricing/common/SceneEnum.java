package com.gwang.test.testng.pricing.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 定价场景枚举，用以单元测试使用
 */
public enum SceneEnum {

    FAST(1001l, "200_0_2002"),
    ;

    SceneEnum(Long id, String code) {
        this.id = id;
        this.code = code;
    }

    private long id;
    private String code;

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    private static Map<Long, SceneEnum> id2Enum = new HashMap<>();
    private static Map<String, SceneEnum> code2Enum = new HashMap<>();

    static {
        for (SceneEnum typeEnum : SceneEnum.values()) {
            id2Enum.put(typeEnum.getId(), typeEnum);
        }

        for (SceneEnum typeEnum : SceneEnum.values()) {
            code2Enum.put(typeEnum.getCode(), typeEnum);
        }
    }

    public static SceneEnum getEnumByCode(String code) {
        return code2Enum.get(code);
    }

    public static SceneEnum getEnumById(Long id) {
        return id2Enum.get(id);
    }
}
