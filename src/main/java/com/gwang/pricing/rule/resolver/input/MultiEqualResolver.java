package com.gwang.pricing.rule.resolver.input;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Splitter;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.InputResolver;

import java.util.List;

public class MultiEqualResolver extends InputResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        JSONObject ruleData = (JSONObject) rule.getData();
        if (ruleData == null) {
            return new MatchResult(Boolean.FALSE);
        }
        String expectValues = TypeUtils.castToString(getExpect(ruleData));
        List<String> expects = Splitter.on(",").splitToList(expectValues);
        String fact = TypeUtils.castToString(ctx.getFactorValue(rule.getFactorKey()));
        for (String expect : expects) {
            if(expect.equals(fact)) {
                return new MatchResult(Boolean.TRUE, rule.getData());
            }
        }
        return new MatchResult(Boolean.FALSE);
    }
}
