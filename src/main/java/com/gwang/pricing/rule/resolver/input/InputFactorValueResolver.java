package com.gwang.pricing.rule.resolver.input;


import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.InputResolver;

import java.math.BigDecimal;
import java.util.Map;

public class InputFactorValueResolver extends InputResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        return new MatchResult(Boolean.TRUE, rule.getData());
    }

    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
       return TypeUtils.castToBigDecimal(ctx.getFactorValue(rule.getFactorKey()));
    }
}
