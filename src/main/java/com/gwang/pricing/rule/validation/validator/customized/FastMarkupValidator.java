package com.gwang.pricing.rule.validation.validator.customized;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.pricing.rule.validation.ValidationResult;
import com.gwang.pricing.rule.validation.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class FastMarkupValidator extends AbstractValidator {
    private static final int maxTime = 3600;

    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        BigDecimal markupStepMax = new BigDecimal(params.get(0));
        BigDecimal markupStepMin = new BigDecimal(params.get(1));
        BigDecimal markupUplimitMin = new BigDecimal(params.get(2));
        BigDecimal markupUplimitMax = new BigDecimal(params.get(3));

        JSONArray markupRules = (JSONArray) contexts.get(0).values().iterator().next();
        for (Object markupRule : markupRules) {
            // 校验
            JSONObject rule = (JSONObject) markupRule;
            String start = rule.getString(ResolverConstants.START);
            String end = rule.getString(ResolverConstants.END);
            Integer ignoreWeather = rule.getInteger(ResolverConstants.IGNORE_WEATHER);
            String normalStartStr = rule.getString(ResolverConstants.NORMAL_START);
            String preBookStartStr = rule.getString(ResolverConstants.PRE_BOOK_START);
            String preBookRemainStr = rule.getString(ResolverConstants.PRE_BOOK_REMAIN);
            String stepStr = rule.getString(ResolverConstants.STEP);
            BigDecimal stepFee = rule.getBigDecimal(ResolverConstants.STEP_FEE);
            BigDecimal upLimit = rule.getBigDecimal(ResolverConstants.UP_LIMIT);

            if (!parseStrTime(normalStartStr, "mm:ss") || !parseStrTime(preBookRemainStr, "mm:ss")
                    || !parseStrTime(preBookStartStr, "mm:ss") || !parseStrTime(stepStr, "mm:ss")
                    || !parseStrTime(start, "HH:mm") || !parseStrTime(end, "HH:mm")) {
                result.merge(new ValidationResult(1, "输入了非法的时间参数！"));
            }

            if (stepFee == null || stepFee.compareTo(markupStepMax) > 0 || stepFee.compareTo(markupStepMin) < 0) {
                result.merge(new ValidationResult(1, String.format("%s-%s，单次加价金额必须大于等于%2.1f元,不能大于%2.1f元！", start, end, markupStepMin, markupStepMax)));
            }

            if (upLimit == null || upLimit.compareTo(markupUplimitMax) > 0 || upLimit.compareTo(markupStepMin) < 0) {
                result.merge(new ValidationResult(1, String.format("%s-%s，加价总额上限必须大于等于%2.1f元,不能大于%2.1f元！", start, end, markupUplimitMin, markupUplimitMax)));
            }

            if (transFromMinsToSeconds(normalStartStr, "normalStart", result) > maxTime) {
                result.merge(new ValidationResult(1, String.format("%s-%s，普通单首次加价时间不能大于60分钟！", start, end)));
            }

            if (transFromMinsToSeconds(preBookStartStr, "preBookStart", result) > maxTime) {
                result.merge(new ValidationResult(1, String.format("%s-%s，预订单首次加价时间不能大于60分钟！", start, end)));
            }

            if (transFromMinsToSeconds(stepStr, "step", result) > maxTime) {
                result.merge(new ValidationResult(1, String.format("%s-%s，加价时间间隔不能大于60分钟！", start, end)));
            }

            if (transFromMinsToSeconds(stepStr, "step", result) < 30) {
                result.merge(new ValidationResult(1, String.format("%s-%s，加价时间间隔不能小于30秒！", start, end)));
            }


            if (ignoreWeather == null || ignoreWeather == 0) {
                result.merge(new ValidationResult(1, String.format("雨天加价选项无法识别，%s", ignoreWeather)));
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "动态加价补贴校验失败";
    }

    private boolean parseStrTime(String strTime, String format) {
        if (StringUtils.isEmpty(strTime)) {
            return false;
        }
        if (strTime.contains("-")) {
            return false;
        }
        SimpleDateFormat formatTime = new SimpleDateFormat(format);
        try {
            formatTime.parse(strTime);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    private int transFromMinsToSeconds(String strTime, String strName, ValidationResult result) {
        if (StringUtils.isEmpty(strTime)) {
            result.merge(new ValidationResult(1, strName + " 输入了非法的时间参数"));
            return 30;
        }
        String timeStr[] = strTime.split(":");
        if (timeStr == null || timeStr.length != 2) {
            result.merge(new ValidationResult(1, strName + " 日期格式不为:[分:秒]格式，解析出错"));
            return 30;
        }
        try {
            int min = Integer.parseInt(timeStr[0]);
            int second = Integer.parseInt(timeStr[1]);
            return min * 60 + second;
        } catch (Exception e) {
            result.merge(new ValidationResult(1, strName + " 输入了非法的时间参数"));
            return 30;
        }
    }

}
