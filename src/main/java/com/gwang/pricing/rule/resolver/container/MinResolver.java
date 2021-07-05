package com.gwang.pricing.rule.resolver.container;


import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;

import java.math.BigDecimal;
import java.util.Map;

public class MinResolver extends MultiOrResolver {
    /**
     * and容器，中间规则的都是匹配规则，最后一个规则是计算规则。
     * @param ctx
     * @param currentFormulaContext
     * @param rule  @return
     */
    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        BigDecimal min = new BigDecimal(Integer.MAX_VALUE);
        for (Rule child : rule.getChildren()) {
            BigDecimal childResult = child.calculate(ctx, currentFormulaContext);
            if(min.compareTo(childResult) > 0) {
                min = childResult;
            }
        }
        return min;
    }

}
