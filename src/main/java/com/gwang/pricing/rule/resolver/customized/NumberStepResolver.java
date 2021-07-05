package com.gwang.pricing.rule.resolver.customized;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
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

public class NumberStepResolver extends CustomizedResolver {

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
        List<Object> matchedRules = (List<Object>) rule.getMatchedData();
        BigDecimal fact = TypeUtils.castToBigDecimal(ctx.getFactorValue(rule.getFactorKey()));
        BigDecimal result = BigDecimal.ZERO;
        for (Object  matchedRule : matchedRules) {
            JSONObject data = (JSONObject) matchedRule;
            BigDecimal baseFee = data.getBigDecimal(ResolverConstants.BASE_FEE);
            BigDecimal start = data.getBigDecimal(ResolverConstants.START);
            BigDecimal step = data.getBigDecimal(ResolverConstants.STEP);
            BigDecimal stepFee = data.getBigDecimal(ResolverConstants.STEP_FEE);
            BigDecimal end = data.getBigDecimal(ResolverConstants.END);
            if(fact.compareTo(end) < 0) {
                end = fact;
            }
            result = result.add(baseFee.add(end.subtract(start).divide(step).multiply(stepFee)));
        }
        return result;
    }

}
