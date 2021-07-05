package com.gwang.pricing.rule.resolver.container;


import com.google.common.collect.Lists;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ContainerResolver;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class OrResolver extends ContainerResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        List<Rule> matchedRules = Lists.newArrayList();
        for (Rule child : rule.getChildren()) {
            if(child.match(ctx)) {
                matchedRules.add(child);
                rule.setMatchedChildren(matchedRules);
                return new MatchResult(Boolean.TRUE);
            }
        }
        return new MatchResult(Boolean.FALSE);
    }

    /**
     * or容器，如果是可以计算的规则，匹配的第一个规则就是需要计算的规则。
     * @param ctx
     * @param currentFormulaContext
     * @param rule  @return
     */
    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        List<Rule> matchedChildren = rule.getMatchedChildren();
        Rule matched = matchedChildren.get(0);
        return matched.calculate(ctx, currentFormulaContext);
    }
}
