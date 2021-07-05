package com.gwang.pricing.rule.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.gwang.pricing.rule.ResolverFactory;
import com.gwang.pricing.rule.resolver.ContainerResolver;
import com.gwang.pricing.rule.resolver.CustomizedResolver;
import com.gwang.pricing.rule.resolver.InputResolver;
import com.gwang.pricing.rule.resolver.Resolver;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Rule {
    @JSONField(name = "r")
    private String resolverKey;
    @JSONField(name="rp")
    private Object resolverProperties;
    @JSONField(name = "n")
    private String name;
    @JSONField(name = "f")
    private String factorKey;
    @JSONField(name = "d")
    private Object data;
    @JSONField(name = "c")
    private List<Rule> children;
    @JSONField(name="i")
    private String formula;

    private Resolver resolver;
    private List<Rule> matchedChildren = Lists.newArrayList();
    private Object matchedData;
    private BigDecimal calculate;

    public Rule() {

    }
    public Rule(Resolver resolver, String resolverKey, String factorKey, Object ruleData, List<Rule> children) {
        this.resolver = resolver;
        this.resolverKey = resolverKey;
        this.factorKey = factorKey;
        this.data = ruleData;
        this.children = children;
    }

    public void injectData(PricingContext ctx, RuleData data) {
        Resolver resolver = this.getResolver();
        if(resolver instanceof ContainerResolver) {
            List<RuleData> childrenData = data.getChildrenData();
            List<Rule> children = this.getChildren();
            int size = childrenData.size() - children.size();
            if(size != 0){//表示容器组件是个多行组件，需要以配置的规则为模版，生成多个规则实例。
                Rule child = children.get(0);
                for(int i = 0; i< size; i++) {
                    children.add(child.copy());
                }
            }
            for (int i = 0; i < childrenData.size(); i++) {
                children.get(i).injectData(ctx, childrenData.get(i));
            }
        } else {
            this.setData(data.getData());
        }
    }

    public boolean match(PricingContext ctx) {
        resolver = getResolver();
        MatchResult result = resolver.match(ctx, this);
        if(result.isMatched() && (resolver instanceof InputResolver || resolver instanceof CustomizedResolver)) {
            this.setMatchedData(result.getMatchedData());
        }
        return result.isMatched();
    }

    public BigDecimal calculate(PricingContext ctx, Map<String, Object> currentFormulaContext) {
        this.calculate = resolver.calculate(ctx, currentFormulaContext, this);
        return this.calculate;
    }

    public Resolver getResolver() {
        if(resolver == null) {
//            String resolverName = ctx.getResolverName(TypeUtils.castToLong(resolverKey));
            resolver = ResolverFactory.getResolver(resolverKey);
        }
        return resolver;
    }

    public Rule copy() {
        Rule rule = new Rule();
        rule.setName(this.name);
        rule.setResolverKey(this.resolverKey);
        rule.setFactorKey(this.factorKey);
        rule.setFormula(this.formula);
        rule.setChildren(Lists.<Rule>newArrayList());
        rule.setResolverProperties(this.resolverProperties);
        if(CollectionUtils.isEmpty(this.getChildren())) {
            return rule;
        }
        for (Rule child : this.getChildren()) {
            rule.getChildren().add(child.copy());
        }
        return rule;
    }
    public void setResolver(Resolver resolver) {
        this.resolver = resolver;
    }

    public Object getResolverProperties() {
        return resolverProperties;
    }

    public void setResolverProperties(Object resolverProperties) {
        this.resolverProperties = resolverProperties;
    }

    public String getResolverKey() {
        return resolverKey;
    }

    public void setResolverKey(String resolverKey) {
        this.resolverKey = resolverKey;
    }

    public String getFactorKey() {
        return factorKey;
    }

    public void setFactorKey(String factorKey) {
        this.factorKey = factorKey;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<Rule> getChildren() {
        return children;
    }

    public void setChildren(List<Rule> children) {
        this.children = children;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public List<Rule> getMatchedChildren() {
        return matchedChildren;
    }

    public void setMatchedChildren(List<Rule> matchedChildren) {
        this.matchedChildren = matchedChildren;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getMatchedData() {
        return matchedData;
    }

    public void setMatchedData(Object matchedData) {
        this.matchedData = matchedData;
    }

    public BigDecimal getCalculate() {
        return calculate;
    }

    public void setCalculate(BigDecimal calculate) {
        this.calculate = calculate;
    }
}
