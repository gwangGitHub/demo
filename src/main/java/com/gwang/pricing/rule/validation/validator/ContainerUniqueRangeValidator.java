package com.gwang.pricing.rule.validation.validator;

import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.collect.Lists;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.gwang.pricing.rule.validation.ValidationResult;

import java.util.List;
import java.util.Map;

public class ContainerUniqueRangeValidator extends AbstractValidator {

    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        List<NumberRange> ranges = Lists.newArrayList();
        for (Map<String, Object> context : contexts) {
            Expression startExpression = AviatorEvaluator.compile(params.get(0), true);
            double start = TypeUtils.castToDouble(startExpression.execute(context));

            Expression endExpression = AviatorEvaluator.compile(params.get(1), true);
            double end = TypeUtils.castToDouble(endExpression.execute(context));
            NumberRange range = new NumberRange(start, end);
            ranges.add(range);
        }
        for (int i = 0; i < ranges.size(); i++) {
            for (int j = i+1; j < ranges.size(); j++) {
                if(ranges.get(i).isIntersectWith(ranges.get(j))) {
                    result.merge(new ValidationResult(1, message));
                }
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "区间无交集校验失败";
    }
}
