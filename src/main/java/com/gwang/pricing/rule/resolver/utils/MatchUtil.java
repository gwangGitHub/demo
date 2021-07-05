package com.gwang.pricing.rule.resolver.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.collect.Lists;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ResolverConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MatchUtil {

    public static List<Object> getRangeMatchedData(PricingContext ctx, Rule rule) {
        JSONArray dataArray = (JSONArray) rule.getData();
        List<Object> matched = Lists.newArrayList();
        Object ruleProperties = rule.getResolverProperties();
        boolean isMultiRangeMatch = getPropertyBooleanValue(ruleProperties, ResolverConstants.MULTI_MATCH);
        boolean leftClose = getPropertyBooleanValue(ruleProperties, ResolverConstants.LEFT_CLOSE);
        boolean rightClose = getPropertyBooleanValue(ruleProperties, ResolverConstants.RIGHT_CLOSE);

        for (Object data : dataArray) {
            boolean result = false;
            if (isMultiRangeMatch) {//多区间匹配，只匹配区间开始就认为是匹配上了
                result = MatchUtil.isMatchRangeStart(ctx, (JSONObject) data, rule, leftClose, rightClose);
            } else {
                result = MatchUtil.isMatchRange(ctx, (JSONObject) data, rule, leftClose, rightClose);
            }
            if(result) {
                matched.add(data);
                //是否匹配多个
                if(!isMultiRangeMatch) {
                    return matched;
                }
            }
        }
        return matched;
    }

    private static boolean isMatchRangeStart(PricingContext ctx, JSONObject data, Rule rule, boolean leftClose, boolean rightClose) {
        Double start = data.getDouble(ResolverConstants.START);
        Double fact = TypeUtils.castToDouble(ctx.getFactorValue(rule.getFactorKey()));
        return compare(start, fact, leftClose);
    }

    private static boolean isMatchRange(PricingContext ctx, JSONObject data, Rule rule, boolean leftClose, boolean rightClose) {
        Double start = data.getDouble(ResolverConstants.START);
        Double end = data.getDouble(ResolverConstants.END);
        Double fact = TypeUtils.castToDouble(ctx.getFactorValue(rule.getFactorKey()));

        return compare(start, fact, leftClose) && compare(fact, end, rightClose);
    }

    public static boolean dateTimeRangeMatch(String startTimeStr, String endTimeStr, long fact) {
        long start = DateUtil.parseTimeStr2Seconds(startTimeStr);
        long end = DateUtil.parseTimeStr2Seconds(endTimeStr);
        return compare(Double.valueOf(start), Double.valueOf(fact), Boolean.TRUE)
                && compare(Double.valueOf(fact), Double.valueOf(end), Boolean.FALSE);
    }

    public static boolean timeRangeMatch(String startTimeStr, String endTimeStr, long fact) {
        String day = DateUtil.Date2String(DateUtil.fromUnixTime(fact));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTimeStr = day + " " + startTimeStr + ":00";
        endTimeStr = day + " " + endTimeStr + ":59";

        try {
            long start = DateUtil.date2Unixtime(formatter.parse(startTimeStr));
            long end = DateUtil.date2Unixtime(formatter.parse(endTimeStr));
            return compare(Double.valueOf(start), Double.valueOf(fact), Boolean.TRUE)
                    && compare(Double.valueOf(fact), Double.valueOf(end), Boolean.FALSE);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean compare(Double left, Double right, boolean isEqual) {
        if(isEqual && left.compareTo(right) == 0) {
            return true;
        }
        return left.compareTo(right) < 0;
    }

    public static boolean getPropertyBooleanValue(Object ruleProperties, String key) {
        if(ruleProperties == null) {
            return false;
        }
        JSONObject resolverProperties = (JSONObject) ruleProperties;
        return resolverProperties.getBooleanValue(key);
    }
}
