package com.gwang.pricing.rule.resolver.container;


import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class SumResolver extends MultiOrResolver {
    /**
     * and容器，中间规则的都是匹配规则，最后一个规则是计算规则。
     * @param ctx
     * @param currentFormulaContext
     * @param rule  @return
     */
    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        List<Rule> children = rule.getMatchedChildren();
        BigDecimal result = BigDecimal.ZERO;
        for (Rule child : children) {
            result = result.add(child.calculate(ctx, currentFormulaContext));
        }
        return result;
    }

}
