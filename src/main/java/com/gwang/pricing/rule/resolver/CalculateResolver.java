package com.gwang.pricing.rule.resolver;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.collect.Maps;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.gwang.pricing.common.LocalLoggerUtil;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Map;

public abstract class CalculateResolver implements Resolver {

    private static Logger logger = LoggerFactory.getLogger(CalculateResolver.class);

    public BigDecimal calculate(PricingContext ctx, Map<String, Object> parentFormulaContext, Rule rule) {
        Map<String, Object> currentFormulaContext = parentFormulaContext;
        if(parentFormulaContext == null) {
            currentFormulaContext = ctx.getContext();
        }

        if(this instanceof InputResolver && StringUtils.isNotBlank(rule.getName())) {
            JSONObject ruleData = (JSONObject) rule.getMatchedData();
            String inputValue = TypeUtils.castToString(ruleData.get(ResolverConstants.VALUE));
            if (NumberUtils.isNumber(inputValue)) {
                currentFormulaContext.put(rule.getName(), TypeUtils.castToBigDecimal(inputValue));
            } else {
                currentFormulaContext.put(rule.getName(), inputValue);
            }
        }

        if(this instanceof ContainerResolver && StringUtils.isNotBlank(rule.getName())) {
            currentFormulaContext = Maps.newHashMap();
            //如果父作用域不为空则加入到父作用域
            if(parentFormulaContext != null) {
                parentFormulaContext.put(rule.getName(), currentFormulaContext);
            }
        }

        BigDecimal result = BigDecimal.ZERO;
        if(StringUtils.isNotBlank(rule.getFormula())) {
            result = formulaCalculate(ctx, currentFormulaContext, rule);
        } else {
            result = ruleCalculate(ctx, currentFormulaContext, rule);
        }

        //TODO 新逻辑，担心影响老逻辑，且没有时间对比验证再灰度，故这里配置只针对新模板生效
        if (ctx.isCalNewLogic() && parentFormulaContext != null && StringUtils.isNotBlank(rule.getName())) {
            LocalLoggerUtil.logForTestWithJSON(logger, "封顶值计算，使用新逻辑");
            parentFormulaContext.put(rule.getName(), result);
//            EngineCatMetricUtil.metricForNewLogic("calNew", true);
        }

        return result;
    }

    protected abstract BigDecimal ruleCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule);

    private BigDecimal formulaCalculate(PricingContext ctx, Map<String, Object> currentFormulaContext, Rule rule) {
        if(CollectionUtils.isNotEmpty(rule.getMatchedChildren())) {
            for (Rule matchedChild : rule.getMatchedChildren()) {
                matchedChild.calculate(ctx, currentFormulaContext);
            }
        }
        Expression expression = AviatorEvaluator.compile(rule.getFormula(), true);
        Map<String, Object> formulaContext = Maps.newHashMap(ctx.getContext());
        formulaContext.putAll(currentFormulaContext);
        LocalLoggerUtil.logForTestWithJSON(logger, "formulaCalculate, formula:{}, context:{}", rule.getFormula(), formulaContext);
        Object result = expression.execute(formulaContext);
        if (result instanceof Number) {
            return BigDecimal.valueOf(((Number) result).doubleValue());
        }
        return BigDecimal.ZERO;

        //TODO complex解析器暂时不支持公式
//        if(rule.getMatchedData() instanceof List) {
//            BigDecimal sum = BigDecimal.ZERO;
//            for(Object matched : (List)rule.getMatchedData()) {
//                sum.add(calculateRuleFormula(matched, rule));
//            }
//            return sum;
//        }
    }
}
