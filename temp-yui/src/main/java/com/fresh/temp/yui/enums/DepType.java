package com.fresh.temp.yui.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fresh.xy.mb.core.EnumValue;

import java.util.Arrays;
import java.util.Objects;

public enum DepType {
    SYSTEM("SYSTEM", "系统"),
    USER("USER", "用户")/*,
    NULL(null, "null值测试")*/;

    @JsonValue
    @EnumValue
    private String value;
    private String text;

    DepType(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static DepType getByValue(String v) {
        DepType result = Arrays.stream(DepType.values()).filter(per -> Objects.equals(per.value, v)).findFirst().orElse(null);
        return result;
    }

}
