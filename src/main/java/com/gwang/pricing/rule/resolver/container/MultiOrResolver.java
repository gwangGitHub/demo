package com.gwang.pricing.rule.resolver.container;

import com.google.common.collect.Lists;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ContainerResolver;

import java.util.List;

public abstract class MultiOrResolver extends ContainerResolver {
    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        boolean isMatch = false;
        List<Rule> matchedRules = Lists.newArrayList();
        for (Rule child : rule.getChildren()) {
            if(child.match(ctx)) {
                matchedRules.add(child);
                isMatch = true;
            }
        }
        rule.setMatchedChildren(matchedRules);
        return new MatchResult(isMatch);
    }
}
