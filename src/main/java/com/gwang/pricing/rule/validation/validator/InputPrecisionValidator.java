package com.gwang.pricing.rule.validation.validator;

import com.alibaba.fastjson.util.TypeUtils;
import com.google.common.base.Splitter;
import com.gwang.pricing.rule.validation.ValidationResult;

import java.util.List;
import java.util.Map;

public class InputPrecisionValidator extends AbstractValidator {
    @Override
    public ValidationResult validateLogic(List<Map<String, Object>> contexts, List<String> params, String message) {
        ValidationResult result = new ValidationResult();
        for (Map<String, Object> context : contexts) {
            String value = TypeUtils.castToString(context.get(params.get(0)));
            Integer precision = TypeUtils.castToInt(params.get(1));
            List<String> parts = Splitter.on(".").splitToList(value);
            if(parts.size() > 1 && parts.get(1).length() > precision) {
                result.merge(new ValidationResult(1, message));
            }
        }
        return result;
    }

    @Override
    public String getErrorMessage() {
        return "精度校验失败";
    }
}
