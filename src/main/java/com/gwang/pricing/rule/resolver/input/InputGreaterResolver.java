package com.gwang.pricing.rule.resolver.input;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.InputResolver;
import com.gwang.pricing.rule.resolver.ResolverConstants;

import java.math.BigDecimal;

public class InputGreaterResolver extends InputResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        JSONObject ruleData = (JSONObject) rule.getData();
        if (ruleData == null) {
            return new MatchResult(Boolean.FALSE);
        }
        Object type = ruleData.get(ResolverConstants.DATA_TYPE);

        if(isDateTimeType(type)) {
            int fact = TypeUtils.castToInt(ctx.getFactorValue(rule.getFactorKey()));
            int expect = getDateTimeTypeExpect(ruleData, type, fact);
            if(fact > expect) {
                return new MatchResult(Boolean.TRUE, rule.getData());
            }
        } else {
            BigDecimal expect = TypeUtils.castToBigDecimal(getExpect(ruleData));
            BigDecimal fact = TypeUtils.castToBigDecimal(ctx.getFactorValue(rule.getFactorKey()));
            if(fact.compareTo(expect) > 0) {
                return new MatchResult(Boolean.TRUE, rule.getData());
            }
        }
        return new MatchResult(Boolean.FALSE);
    }
}
