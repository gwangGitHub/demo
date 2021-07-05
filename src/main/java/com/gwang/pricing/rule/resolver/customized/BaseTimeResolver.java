package com.gwang.pricing.rule.resolver.customized;


import com.alibaba.fastjson.JSONObject;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.CustomizedResolver;

public abstract class BaseTimeResolver extends CustomizedResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        JSONObject data = (JSONObject) rule.getData();
        boolean compareResult = isRangeMatch(ctx, data, rule);
        Object matchedData = null;
        if (compareResult) {
            matchedData = data;
        }
        return new MatchResult(compareResult, matchedData);
    }

    protected abstract boolean isRangeMatch(PricingContext ctx, JSONObject data, Rule rule);
}
