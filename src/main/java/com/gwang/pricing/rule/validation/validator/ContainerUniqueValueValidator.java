package com.gwang.pricing.rule.validation.validator;

import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.collect.Sets;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.gwang.pricing.rule.validation.ValidationResult;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContainerUniqueValueValidator extends AbstractValidator {

    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        Set<String> existValue = Sets.newHashSet();
        for (Map<String, Object> context : contexts) {
            Expression startExpression = AviatorEvaluator.compile(params.get(0), true);
            String value = TypeUtils.castToString(startExpression.execute(context));
            if(existValue.contains(value)) {
                result.merge(new ValidationResult(1, message));
            } else {
                existValue.add(value);
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "取值唯一性校验失败";
    }
}
