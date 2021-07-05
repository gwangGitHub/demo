package com.gwang.pricing.rule.resolver;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;

import java.math.BigDecimal;
import java.util.Map;

public abstract class CustomizedResolver extends CalculateResolver {
    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        JSONObject ruleData = (JSONObject) rule.getMatchedData();
        Object calData = ruleData.get(ResolverConstants.VALUE);
        if(calData != null) {
            return TypeUtils.castToBigDecimal(ruleData.get(ResolverConstants.VALUE));
        }
        return BigDecimal.ZERO;
    }
}
