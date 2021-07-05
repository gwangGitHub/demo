package com.gwang.pricing.rule.resolver.input;


import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.InputResolver;

public class InputFixValueResolver extends InputResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        return new MatchResult(Boolean.TRUE, rule.getData());
    }
}
