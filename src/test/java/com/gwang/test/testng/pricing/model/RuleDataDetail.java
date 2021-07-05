package com.gwang.test.testng.pricing.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.gwang.pricing.rule.resolver.ResolverConstants;

public class RuleDataDetail {

    @JSONField(name = ResolverConstants.VALUE)
    private Object value;

    @JSONField(name = ResolverConstants.DATA_TYPE)
    private Object type;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
}
