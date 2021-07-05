package com.gwang.pricing.rule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.model.RuleData;
import com.gwang.pricing.rule.resolver.*;
import com.gwang.pricing.rule.resolver.utils.MatchUtil;
import com.gwang.pricing.rule.validation.ValidatorConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class RuleUtils {

    public static String transRuleToComplexFormat(String ruleTemplate, String simpleRuleData, PricingContext ctx) {
        Rule root = JSON.parseObject(ruleTemplate, Rule.class);

        JSONObject simpleRule = JSON.parseObject(simpleRuleData);
        RuleData ruleData = injectDataToRule(root, simpleRule, ctx);
        return JSON.toJSONString(ruleData);
    }

    public static String transRuleToSimpleFormat(String ruleTemplate, String complexJson) {
        PricingContext ctx = initPricingContext();
        Rule root = JSON.parseObject(ruleTemplate, Rule.class);
        RuleData dataRoot = JSON.parseObject(complexJson, RuleData.class);
        root.injectData(ctx, dataRoot);
        return transRuleToSimpleFormat(root, ctx, false);
    }

    public static String transRuleToSimpleFormat(Rule rule, PricingContext ctx, boolean isParentList) {
        Resolver resolver = rule.getResolver();
        Object properties = rule.getResolverProperties();
        boolean isRule = MatchUtil.getPropertyBooleanValue(properties, "ir");
        boolean isList = MatchUtil.getPropertyBooleanValue(properties, "ia");
        if(isRule) {
            if(resolver instanceof ContainerResolver) {
                String prefix = getStartMark(isList);
                String suffix = getEndMark(isList);

                if(!isParentList && StringUtils.isNotBlank(rule.getName()) && !ValidatorConstants.RULE_NAME_GLOBAL.equals(rule.getName())) {
                    prefix = "\"" + rule.getName() + "\":" + prefix;
                }
                return prefix + getListJson(rule.getChildren(), ctx, isList) + suffix;
            }
            if(resolver instanceof CustomizedResolver) {
                if (StringUtils.isNotBlank(rule.getName()) && !isParentList) {
                    return "\"" + rule.getName() + "\":" + rule.getData();
                }
                return (String) rule.getData();
            }
            JSONObject ruleData = (JSONObject) rule.getData();
            String calData = "";
            if (ruleData != null) {
                calData = TypeUtils.castToString(ruleData.get(ResolverConstants.VALUE));
            }
            return "\"" + rule.getName() + "\":\"" + calData + "\"";
        }
        if(resolver instanceof ContainerResolver) {
            return getListJson(rule.getChildren(), ctx, isParentList);
        }
        return "";
    }

    private static RuleData injectDataToRule(Rule rule, Object simpleRule, PricingContext ctx) {
        Object properties = rule.getResolverProperties();
        Resolver resolver = rule.getResolver();
        RuleData ruleData = new RuleData();
        if(resolver instanceof InputResolver) {
            JSONObject obj = new JSONObject();
            String ruleValue = String.valueOf(simpleRule);
            if (StringUtils.isNotBlank(ruleValue)) {
                if (isDateTime(ruleValue)) {
                    obj.put(ResolverConstants.DATA_TYPE, ResolverConstants.DT_TYPE_DATE_TIME);
                } else if (isDate(ruleValue)) {
                    obj.put(ResolverConstants.DATA_TYPE, ResolverConstants.DT_TYPE_DATE);
                } else if (isTime(ruleValue)) {
                    obj.put(ResolverConstants.DATA_TYPE, ResolverConstants.DT_TYPE_TIME);
                }
            }
            obj.put(ResolverConstants.VALUE, ruleValue);
            ruleData.setData(obj);
            return ruleData;
        }

        if (resolver instanceof CustomizedResolver) {
            ruleData.setData(simpleRule);
            return ruleData;
        }
        List<Rule> children = rule.getChildren();
        List<RuleData> childrenData = Lists.newArrayList();
        ruleData.setChildrenData(childrenData);

        boolean isRule = MatchUtil.getPropertyBooleanValue(properties, "ir");
        boolean isList = MatchUtil.getPropertyBooleanValue(properties, "ia");
        if(isRule && isList) {
            if (simpleRule == null) {
                return ruleData;
            }
            JSONArray ruleObjects = (JSONArray) simpleRule;
            int appendNum = ruleObjects.size() - children.size();
            Rule sampleRule = children.get(0);
            for (int i=0; i<appendNum; i++) {
                children.add(sampleRule.copy());
            }
            for(int i =0; i<ruleObjects.size(); i++) {
                childrenData.add(injectDataToRule(children.get(i), ruleObjects.get(i), ctx));
            }
            return ruleData;
        }

        for (Rule child : children) {
            Resolver childResolver = child.getResolver();
            boolean isCList = MatchUtil.getPropertyBooleanValue(child.getResolverProperties(), "ia");
            if(childResolver instanceof ContainerResolver) {
                if(isCList) {
                    childrenData.add(injectDataToRule(child, ((JSONObject) simpleRule).get(child.getName()), ctx));
                } else {
                    childrenData.add(injectDataToRule(child, simpleRule, ctx));
                }
            } else if(childResolver instanceof InputResolver || childResolver instanceof CustomizedResolver) {
                childrenData.add(injectDataToRule(child, ((JSONObject) simpleRule).get(child.getName()), ctx));
            }
        }
        return ruleData;
    }

    private static String getListJson(List<Rule> children, PricingContext ctx, boolean isParentList) {
        List<String> childrenJson = Lists.newArrayList();
        for (int i = 0; i < children.size(); i++) {
            String json = transRuleToSimpleFormat(children.get(i), ctx, isParentList);
            if (StringUtils.isNotBlank(json)) {
                childrenJson.add(json);
            }
        }
        return Joiner.on(",").join(childrenJson);
    }

    private static String getStartMark(boolean isList) {
        if(isList) {
            return "[";
        }
        return "{";
    }

    private static String getEndMark(boolean isList) {
        if(isList) {
            return "]";
        }
        return "}";
    }

    public static void main(String[] args) {
        String time = "10:00";
        String date = "2012-01-20";
        String dateTime = "2012-01-20 10:00:00";
        System.out.println(isTime(dateTime));
        System.out.println(isDate(dateTime));
        System.out.println(isDateTime(dateTime));
    }

    public static boolean isTime(String value) {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm");
        try {
            format.parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDate(String value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isDateTime(String value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            format.parse(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static PricingContext initPricingContext() {
        PricingContext ctx = new PricingContext();
        return ctx;
    }


}
