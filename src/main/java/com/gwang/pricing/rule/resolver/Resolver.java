package com.gwang.pricing.rule.resolver;


import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;

import java.math.BigDecimal;
import java.util.Map;

public interface Resolver {
    public MatchResult match(PricingContext ctx, Rule rule);
    public BigDecimal calculate(PricingContext ctx, Map<String, Object> parentFormulaContext, Rule rule);
}
