package com.gwang.pricing.rule.validation.validator;



import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.validation.ValidationResult;

import java.util.List;

public interface Validator {
    public ValidationResult validate(List<Rule> rules, List<String> params, String message);
}
