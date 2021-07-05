package com.gwang.pricing.rule.validation.validator;

import com.alibaba.fastjson.util.TypeUtils;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.gwang.pricing.rule.validation.ValidationResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ContainerContinuousRangeValidator extends AbstractValidator {
    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        Map<String, Object> preContext = null;
        for (Map<String, Object> context : contexts) {
            if(preContext != null) {
                Expression startExpression = AviatorEvaluator.compile(params.get(0), true);
                BigDecimal start = TypeUtils.castToBigDecimal(startExpression.execute(context));

                Expression endExpression = AviatorEvaluator.compile(params.get(1), true);
                BigDecimal end = TypeUtils.castToBigDecimal(endExpression.execute(preContext));
                if(start.compareTo(end) != 0) {
                    result.merge(new ValidationResult(1, message));
                }
            }
            preContext = context;
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "区间连续校验失败";
    }
}
