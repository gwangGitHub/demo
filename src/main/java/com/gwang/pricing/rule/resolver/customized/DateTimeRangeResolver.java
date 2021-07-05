package com.gwang.pricing.rule.resolver.customized;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.pricing.rule.resolver.utils.MatchUtil;

public class DateTimeRangeResolver extends BaseTimeResolver {

    @Override
    protected boolean isRangeMatch(PricingContext ctx, JSONObject data, Rule rule) {
        JSONObject resolverProperties = (JSONObject) rule.getResolverProperties();
        String dtType = (String) resolverProperties.get(ResolverConstants.DATA_TYPE);
        String startTimeStr = data.getString(ResolverConstants.START);
        String endTimeStr = data.getString(ResolverConstants.END);
        long fact = TypeUtils.castToLong(ctx.getFactorValue(rule.getFactorKey()));

        if(dtType.equals(ResolverConstants.DT_TYPE_DATE_TIME)) {
            return MatchUtil.dateTimeRangeMatch(startTimeStr, endTimeStr, fact);
        }
        return false;
    }
}
