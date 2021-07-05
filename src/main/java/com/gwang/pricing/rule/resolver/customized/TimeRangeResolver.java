package com.gwang.pricing.rule.resolver.customized;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.pricing.rule.resolver.utils.MatchUtil;

public class TimeRangeResolver extends BaseTimeResolver {

    @Override
    protected boolean isRangeMatch(PricingContext ctx, JSONObject data, Rule rule) {
        String startTimeStr = data.getString(ResolverConstants.START);
        String endTimeStr = data.getString(ResolverConstants.END);
        long fact = TypeUtils.castToLong(ctx.getFactorValue(rule.getFactorKey()));
        return MatchUtil.timeRangeMatch(startTimeStr, endTimeStr, fact);
    }
}
