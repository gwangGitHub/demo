package com.gwang.pricing.rule.resolver.container;


import com.google.common.collect.Lists;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ContainerResolver;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class AndResolver extends ContainerResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        List<Rule> matchedRules = Lists.newArrayList();
        if(CollectionUtils.isEmpty(rule.getChildren())) {
            return new MatchResult(Boolean.FALSE);
        }
        for (Rule child : rule.getChildren()) {
            if(!child.match(ctx)){
                return new MatchResult(Boolean.FALSE);
            } else {
                matchedRules.add(child);
            }
        }
        rule.setMatchedChildren(matchedRules);
        return new MatchResult(Boolean.TRUE);
    }

    /**
     * and容器，中间规则的都是匹配规则，最后一个规则是计算规则。
     * @param ctx
     * @param currentFormulaContext
     * @param rule  @return
     */
    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        List<Rule> matchedChildren = rule.getMatchedChildren();
        BigDecimal result = BigDecimal.ZERO;
        for (Rule matchedChild : matchedChildren) {
            result = matchedChild.calculate(ctx, currentFormulaContext);
        }
        return result;
    }

}
