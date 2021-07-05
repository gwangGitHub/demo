package com.gwang.pricing.rule.resolver;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.utils.DateUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public abstract class InputResolver extends CalculateResolver {
    private static final Logger logger = LoggerFactory.getLogger(InputResolver.class);

    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        JSONObject ruleData = (JSONObject) rule.getMatchedData();
        String calData = TypeUtils.castToString(ruleData.get(ResolverConstants.VALUE));
        if(NumberUtils.isNumber(calData)){
            return TypeUtils.castToBigDecimal(calData);
        }
        return BigDecimal.ZERO;
    }

    protected boolean isDateTimeType(Object type) {
        return isDate(type) || isTime(type) || isDateTime(type);
    }

    private boolean isDate(Object type) {
        return type != null && ResolverConstants.DT_TYPE_DATE.equals(type);
    }

    private boolean isTime(Object type) {
        return type != null && ResolverConstants.DT_TYPE_TIME.equals(type);
    }

    private boolean isDateTime(Object type) {
        return type != null && ResolverConstants.DT_TYPE_DATE_TIME.equals(type);
    }

    protected int getDateTimeTypeExpect(JSONObject ruleData, Object type, int fact) {
        int expect = 0;
        if(isDateTime(type)) {
            expect = getExpectDateTime(ruleData);
        }
        if(isDate(type)) {
            expect = getExpectDate(ruleData);
        }
        if(isTime(type)) {
            expect = getExpectTime(ruleData, fact);
        }
        return expect;
    }

    private int getExpectDate(JSONObject ruleData) {
        String input = TypeUtils.castToString(ruleData.get(ResolverConstants.VALUE));
        input = input + " " + "00" + ":00" + ":00";
        return getStringDateValue(input);
    }

    private int getExpectDateTime(JSONObject ruleData) {
        String expectDateTime = TypeUtils.castToString(ruleData.get(ResolverConstants.VALUE));
        return getStringDateValue(expectDateTime);
    }

    private int getExpectTime(JSONObject ruleData, int fact) {
        String input = TypeUtils.castToString(ruleData.get(ResolverConstants.VALUE));
        if ("23:59".equalsIgnoreCase(input)) {
            logger.info("InputResolver getExpectTime, new input:{}", input);
            input = "24:00";
        }
        input = DateUtil.Date2String(DateUtil.fromUnixTime(fact)) + " " + input + ":00";
        return getStringDateValue(input);
    }

    private int getStringDateValue(String date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return DateUtil.date2Unixtime(formatter.parse(date));
        } catch (ParseException e) {
            //TODO handle exception
        }
        return 0;
    }

    protected Object getExpect(JSONObject ruleData) {
        return ruleData.get(ResolverConstants.VALUE);
    }
}
