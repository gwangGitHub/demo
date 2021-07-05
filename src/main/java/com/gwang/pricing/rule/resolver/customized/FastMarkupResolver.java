package com.gwang.pricing.rule.resolver.customized;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.MatchResult;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.CustomizedResolver;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.pricing.rule.resolver.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class FastMarkupResolver extends CustomizedResolver {

    private static final Logger logger = LoggerFactory.getLogger(FastMarkupResolver.class);

    @Override
    public BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {

        Map<String, Object> context = ctx.getContext();
        //是否预约单
        int isPreBook = TypeUtils.castToInt(context.get(ResolverConstants.IS_PRE_BOOK));
        //运单结算时间点
        int calculateTime = TypeUtils.castToInt(context.get(ResolverConstants.CALCULATE_TIME));
//        int realTime = calculateTime - DateUtil.date2Unixtime(DateUtil.toDay(DateUtil.fromUnixTime(calculateTime)));
        //预约单的预约送达时间
        int deliveredTime = TypeUtils.castToInt(context.get(ResolverConstants.DELIVERED_TIME));
        //指派、推送 的时间点(骑手可见起始时间)
        Integer actionTime = TypeUtils.castToInt(context.get(ResolverConstants.ACTION_TIME));
        //是否有天气补贴
        boolean isHavingWeatherSubsidy = TypeUtils.castToBoolean(context.get(ResolverConstants.IS_HAVING_WEATHER_SUBSIDY));
        if (actionTime == null) {
            logger.info("FastMarkupResolver 参数actionTime找不到，直接返回0");
            return BigDecimal.ZERO;
        }

        //动态加价规则部分
        JSONObject data = (JSONObject) rule.getMatchedData();
        Integer ignoreWeather = data.getInteger(ResolverConstants.IGNORE_WEATHER);
        int normalStartTime = transFromMinsToSeconds(data.getString(ResolverConstants.NORMAL_START));
        int preBookStartTime = transFromMinsToSeconds(data.getString(ResolverConstants.PRE_BOOK_START));
        int preBookRemainTime = transFromMinsToSeconds(data.getString(ResolverConstants.PRE_BOOK_REMAIN));
        int intervalTime = transFromMinsToSeconds(data.getString(ResolverConstants.STEP));
        double markupMoney = data.getBigDecimal(ResolverConstants.STEP_FEE).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        double upLimitMoney = data.getBigDecimal(ResolverConstants.UP_LIMIT).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


        //计算首次加价时间
        int firstMarkupTime;
        if (isPreBook == 1) {
            int bookStartTime = actionTime + preBookStartTime;
            int bookRemainTime = deliveredTime - preBookRemainTime;
            firstMarkupTime = Math.max(bookStartTime, bookRemainTime);
        } else {
            firstMarkupTime = actionTime + normalStartTime;
        }

        //是否是 雨天不加价&&当前有雨补的情况
        if (ignoreWeather != ResolverConstants.RAINY_MARKUP && isHavingWeatherSubsidy) {
            return new BigDecimal(0);
        }

        //判断 达到加价条件
        if (calculateTime < firstMarkupTime || upLimitMoney < markupMoney) {
            return new BigDecimal(0);
        }

        //计算加价次数
        int deltaTime = calculateTime - firstMarkupTime;
        int marksUpTimes = Math.min(deltaTime / intervalTime + 1, (int) (upLimitMoney / markupMoney));

        //动态加价补贴
        return BigDecimal.valueOf(Math.min(upLimitMoney, marksUpTimes * markupMoney));
    }

    @Override
    public MatchResult match(PricingContext ctx, Rule rule) {
        Object matchedData = null;
        boolean hitRule = false;

        for (Object object : (List<?>) rule.getData()) {
            JSONObject data = (JSONObject) object;
            int startTime = transFromHourMinuteToSeconds(data.getString(ResolverConstants.START));
            int endTime = transFromHourMinuteToSeconds(data.getString(ResolverConstants.END));
            int calculateTime = TypeUtils.castToInt(ctx.getContext().get(ResolverConstants.CALCULATE_TIME));
            int realTime = calculateTime - DateUtil.date2Unixtime(DateUtil.toDay(DateUtil.fromUnixTime(calculateTime)));
            hitRule = checkHitRule(startTime, endTime, realTime);

            if (hitRule) {
                matchedData = data;
                break;
            }
        }
        return new MatchResult(hitRule, matchedData);
    }

    //判断 当前结算时间是否在动态补贴的时间间隔内
    private static boolean checkHitRule(int startTime, int endTime, int currentTime) {
        if (endTime > startTime) {
            return currentTime >= startTime && currentTime < endTime;
        } else {
            return currentTime >= startTime || currentTime < endTime;
        }
    }


    //将 mm:ss 转换成Integer
    private int transFromMinsToSeconds(String strTime) {
        if (StringUtils.isEmpty(strTime)) {
            throw new RuntimeException("输入了非法的时间参数");
        } else {
            String[] timeStr = strTime.split(":");
            if (timeStr != null && timeStr.length == 2) {
                try {
                    int min = Integer.parseInt(timeStr[0]);
                    int second = Integer.parseInt(timeStr[1]);
                    return min * 60 + second;
                } catch (Exception var4) {
                    throw new RuntimeException("输入了非法的时间参数");
                }
            } else {
                throw new RuntimeException("日期格式不为:[分:秒]格式，解析出错");
            }
        }
    }

    //将 hh:mm转为Integer
    private int transFromHourMinuteToSeconds(String time) {
        if (StringUtils.isEmpty(time)) {
            throw new RuntimeException("输入了非法的时间参数");
        } else {
            String[] timeStr = time.split(":");
            if (timeStr != null && timeStr.length == 2) {
                try {
                    int hour = Integer.parseInt(timeStr[0]);
                    int minute = Integer.parseInt(timeStr[1]);
                    return 60 * (hour * 60 + minute);
                } catch (Exception var4) {
                    throw new RuntimeException("输入了非法的时间参数");
                }
            } else {
                throw new RuntimeException("日期格式不为:[时:分]格式，解析出错");
            }
        }
    }

}
