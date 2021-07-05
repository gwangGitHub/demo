package com.gwang.pricing.rule.resolver.input;


import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.InputResolver;
import org.apache.commons.lang3.StringUtils;

public class InputBooleanFactorResolver extends InputResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        if(StringUtils.isBlank(rule.getFactorKey())) {
            return new MatchResult(Boolean.FALSE);
        }
        Boolean isFactorTrue = TypeUtils.castToBoolean(ctx.getFactorValue(rule.getFactorKey()));
        if(isFactorTrue) {
            return new MatchResult(Boolean.TRUE, rule.getData());
        }

        return new MatchResult(Boolean.FALSE, rule.getData());
    }
}
