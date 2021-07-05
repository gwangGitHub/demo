package com.gwang.pricing.rule.validation.validator;

import com.alibaba.fastjson.util.TypeUtils;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.gwang.pricing.rule.validation.ValidationResult;

import java.util.List;
import java.util.Map;

public class LessOrEqualValidator extends AbstractValidator {

    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        for (Map<String, Object> context : contexts) {
            Expression startExpression = AviatorEvaluator.compile(params.get(0), true);
            Double left = TypeUtils.castToDouble(startExpression.execute(context));

            Expression endExpression = AviatorEvaluator.compile(params.get(1), true);
            Double right = TypeUtils.castToDouble(endExpression.execute(context));
            if(left > right) {
                result.merge(new ValidationResult(1, message));
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "小于等于校验失败";
    }
}
