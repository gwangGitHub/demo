package com.gwang.pricing.rule.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class RuleData {
    @JSONField(name = "d")
    private Object data;
    @JSONField(name = "c")
    private List<RuleData> childrenData;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<RuleData> getChildrenData() {
        return childrenData;
    }

    public void setChildrenData(List<RuleData> childrenData) {
        this.childrenData = childrenData;
    }
}
