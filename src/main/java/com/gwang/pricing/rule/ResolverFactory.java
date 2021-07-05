package com.gwang.pricing.rule;

import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Options;
import com.gwang.pricing.rule.function.CeilFunction;
import com.gwang.pricing.rule.function.FloorFunction;
import com.gwang.pricing.rule.function.MaxFunction;
import com.gwang.pricing.rule.resolver.*;
import com.gwang.pricing.rule.resolver.container.*;
import com.gwang.pricing.rule.resolver.customized.*;
import com.gwang.pricing.rule.resolver.input.*;

import java.util.Map;

public class ResolverFactory {
    private static final Map<String, Resolver> resolverMap = Maps.newHashMap();
    static {
        resolverMap.put(ResolverConstants.RESOLVER_AND, new AndResolver());
        resolverMap.put(ResolverConstants.RESOLVER_OR, new OrResolver());
        resolverMap.put(ResolverConstants.RESOLVER_MULTI_SUM, new SumResolver());
        resolverMap.put(ResolverConstants.RESOLVER_MULTI_MIN, new MinResolver());
        resolverMap.put(ResolverConstants.RESOLVER_MULTI_MAX, new MaxResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_FIX_VALUE, new InputFixValueResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_NUMBER_EQUAL, new InputNumberEqualResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_STRING_EQUAL, new InputStringEqualResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_GRATER_THAN, new InputGreaterResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL, new InputGreaterEqualResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_LESS_THAN, new InputLessResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_LESS_OR_EQUAL, new InputLessEqualResolver());
        resolverMap.put(ResolverConstants.RESOLVER_INPUT_FACTOR_VALUE, new InputFactorValueResolver());

        resolverMap.put(ResolverConstants.RESOLVER_NUMBER_RANGE, new NumberRangeResolver());
        resolverMap.put(ResolverConstants.RESOLVER_NUMBER_STEP, new NumberStepResolver());
        resolverMap.put(ResolverConstants.RESOLVER_MULTI_VALUE, new MultiEqualResolver());
        resolverMap.put(ResolverConstants.RESOLVER_TIME_RANGE, new TimeRangeResolver());
        resolverMap.put(ResolverConstants.RESOLVER_DATE_TIME_RANGE, new DateTimeRangeResolver());

        resolverMap.put(ResolverConstants.RESOLVER_INPUT_BOOLEAN_FACTOR, new InputBooleanFactorResolver());
        resolverMap.put(ResolverConstants.RESOLVER_FAST_MARKUP, new FastMarkupResolver());

        AviatorEvaluator.addFunction(new FloorFunction());
        AviatorEvaluator.addFunction(new CeilFunction());
        AviatorEvaluator.addFunction(new MaxFunction());
        //https://www.yuque.com/boyan-avfmj/aviatorscript/yr1oau
        //是否将所有浮点数解析为 Decimal 类型，适合需要高精度运算的场景，并且不想为每个浮点数字指定 M 后缀（表示 Decimal 类型）。默认为 false 不开启。
        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_FLOATING_POINT_NUMBER_INTO_DECIMAL, true);
        //是否将整型数字都解析为 BigDecimal，默认为 false，也就是不启用。在所有数字都是需要高精度计算的场景，启用该选项可以减少一些类型转换。
//        AviatorEvaluator.setOption(Options.ALWAYS_PARSE_INTEGRAL_NUMBER_INTO_DECIMAL, true);
    }

    public static Resolver getResolver(String resolverName) {
        return resolverMap.get(resolverName);
    }
}
