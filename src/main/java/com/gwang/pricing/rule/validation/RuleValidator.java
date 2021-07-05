package com.gwang.pricing.rule.validation;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.gwang.pricing.rule.model.Rule;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RuleValidator {

    /**
     * 规则验证入口
     * @param root 待验证规则树
     * @param validationRuleList 验证规则列表
     * @return
     */
    public static ValidationResult validate(Rule root, List<ValidationRule> validationRuleList) {
        if(CollectionUtils.isEmpty(validationRuleList)) {
            return new ValidationResult();
        }
        //1. 获取规则的校验规则，map-><pName,<name, validationRule>>
        Map<String, Multimap<String, ValidationRule>> validationRules = getRuleMap(validationRuleList);
        Set<String> pRuleNames = validationRules.keySet();
        Multimap<String, Rule> pRules = ArrayListMultimap.create();
        //2. 获取待校验的规则作用域(pName对应的规则）
        search(root, pRuleNames, pRules);

        ValidationResult result = new ValidationResult();
        //3. 遍历作用域名称，获取待校验规则，进行校验
        for (String pName : pRules.keySet()) {
            //获取作用域
            Collection<Rule> rules = pRules.get(pName);
            //3.1 获取待校验的规则名称
            Set<String> cRuleNames = validationRules.get(pName).keySet();
            //3.2 获取作用域下的校验规则
            Multimap<String, ValidationRule> validationRulesMap = validationRules.get(pName);

            //3.3 遍历作用域，获取规则，进行校验
            for (Rule rule : rules) {
                Multimap<String, Rule> cRules = ArrayListMultimap.create();
                //3.3.1 获取作用域下的待校验规则
                search(rule, cRuleNames, cRules);
                //3.3.2 用校验规则进行校验
                for (String cRuleName : cRuleNames) {
                    Collection<ValidationRule> cValidationRules = validationRulesMap.get(cRuleName);
                    for (ValidationRule cValidationRule : cValidationRules) {
                        result.merge(cValidationRule.validate((List<Rule>) cRules.get(cRuleName)));
                    }
                }
            }
        }
        return result;
    }

    /**
     *从规则树中获取所有的待校验规则
     * @param rule
     * @param ruleNames
     * @param pRules
     */
    private static void search(Rule rule, Set<String> ruleNames, Multimap<String, Rule> pRules) {
        if(ruleNames.contains(rule.getName())) {
            pRules.put(rule.getName(), rule);
        }
        List<Rule> children = rule.getChildren();
        if(CollectionUtils.isEmpty(children)) {
            return;
        }
        for (Rule child : children) {
            search(child, ruleNames, pRules);
        }
    }

    /**
     * 按规则名称（模版里的rule.name）对校验规则分组
     * @param rules
     * @return
     */
    private static Map<String, Multimap<String, ValidationRule>> getRuleMap(List<ValidationRule> rules) {
        if(CollectionUtils.isEmpty(rules)) {
            return Maps.newHashMap();
        }
        Map<String, Multimap<String, ValidationRule>> ruleMap = Maps.newHashMap();
        for (ValidationRule rule : rules) {
            Multimap<String, ValidationRule> ruleEntry = ruleMap.get(rule.getpName());
            if(ruleEntry == null) {
                ruleEntry = ArrayListMultimap.create();
                ruleMap.put(rule.getpName(), ruleEntry);
            }
            ruleEntry.put(rule.getName(), rule);
        }
        return ruleMap;
    }
}
