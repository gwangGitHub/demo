package com.gwang.pricing.rule.validation.validator;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.CustomizedResolver;
import com.gwang.pricing.rule.resolver.Resolver;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.pricing.rule.validation.ValidationResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

public abstract class AbstractValidator implements Validator {
    @Override
    public ValidationResult validate(List<Rule> rules, List<String> params, String message) {
        List<Map<String, Object>> contexts = buildContext(rules);
        if(StringUtils.isBlank(message)) {
            message = getErrorMessage();
        }
        return validateLogic(contexts, params, message);
    }



    public abstract ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) ;

    protected List<Map<String, Object>> buildContext(List<Rule> rules) {
        List<Map<String, Object>> contexts = Lists.newArrayList();
        for (Rule rule : rules) {
            Map<String, Object> context = Maps.newHashMap();
            buildContext(rule, context);
            contexts.add(context);
        }
        return contexts;
    }

    private void buildContext(Rule rule, Map<String, Object> context) {
        Resolver resolver = rule.getResolver();
        if (resolver instanceof CustomizedResolver && StringUtils.isNotEmpty(rule.getName())) {
            context.put(rule.getName(), rule.getData());
            return;
        }
        if (CollectionUtils.isEmpty(rule.getChildren())) {
            if (StringUtils.isNotEmpty(rule.getName())) {//如果是叶子节点
                JSONObject ruleData = (JSONObject) rule.getData();
                context.put(rule.getName(), ruleData.get(ResolverConstants.VALUE));
            }
        } else {
            for (Rule child : rule.getChildren()) {
                buildContext(child, context);
            }
        }
    }

    public abstract String getErrorMessage();


}
