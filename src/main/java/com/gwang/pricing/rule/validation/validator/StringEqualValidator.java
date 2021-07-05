package com.gwang.pricing.rule.validation.validator;

import com.gwang.pricing.rule.validation.ValidationResult;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.alibaba.fastjson.util.TypeUtils;
import java.util.List;
import java.util.Map;

public class StringEqualValidator extends AbstractValidator {

    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        for (Map<String, Object> context : contexts) {
            Expression startExpression = AviatorEvaluator.compile(params.get(0), true);
            String left = TypeUtils.castToString(startExpression.execute(context));

            Expression endExpression = AviatorEvaluator.compile(params.get(1), true);
            String right = TypeUtils.castToString(endExpression.execute(context));
            if(!left.equals(right)) {
                result.merge(new ValidationResult(1, message));
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "等于校验失败";
    }
}
