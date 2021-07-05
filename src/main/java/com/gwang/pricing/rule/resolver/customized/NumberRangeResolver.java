package com.gwang.pricing.rule.resolver.customized;


import com.alibaba.fastjson.JSONObject;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.CustomizedResolver;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.pricing.rule.resolver.utils.MatchUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class NumberRangeResolver extends CustomizedResolver {

    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        List<Object> matched = MatchUtil.getRangeMatchedData(ctx, rule);
        if(CollectionUtils.isEmpty(matched)) {
            return new MatchResult(Boolean.FALSE);
        }
        return new MatchResult(Boolean.TRUE, matched);
    }

    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        List<Object> matchedRuleDatas = (List<Object>) rule.getMatchedData();
        BigDecimal result = BigDecimal.ZERO;
        for (Object matchedRuleData : matchedRuleDatas) {
            result.add(((JSONObject)matchedRuleData).getBigDecimal(ResolverConstants.VALUE));
        }
        return result;
    }
}
