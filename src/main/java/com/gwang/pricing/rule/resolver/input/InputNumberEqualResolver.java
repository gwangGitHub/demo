package com.gwang.pricing.rule.resolver.input;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.InputResolver;

import java.math.BigDecimal;

public class InputNumberEqualResolver extends InputResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        JSONObject ruleData = (JSONObject) rule.getData();
        if (ruleData == null) {
            return new MatchResult(Boolean.FALSE);
        }
        BigDecimal expect = TypeUtils.castToBigDecimal(getExpect(ruleData));
        BigDecimal fact = TypeUtils.castToBigDecimal(ctx.getFactorValue(rule.getFactorKey()));
        if(expect.equals(fact)) {
            return new MatchResult(Boolean.TRUE, rule.getData());
        }
        return new MatchResult(Boolean.FALSE);
    }
}
