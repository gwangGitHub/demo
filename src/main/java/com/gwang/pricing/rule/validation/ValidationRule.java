package com.gwang.pricing.rule.validation;


import com.alibaba.fastjson.JSONObject;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.validation.validator.Validator;

import java.util.List;

public class ValidationRule {
    private String pName;
    private String name;
    private List<String> params;
    private String message;
    private String validator;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public ValidationResult validate(List<Rule> rules) {
        Validator ruleValidator = ValidatorFactory.getValidator(this.validator);
        return ruleValidator.validate(rules, params, message);
    }

    public static void main(String[] args) {
        String rule = "[{\"pName\":\"table\",\"name\":\"row\",\"message\":\"\",\"validatorName\":\"unique_range\",\"params\":[\"start\",\"end\"]},{\"pName\":\"table\",\"name\":\"row\",\"message\":\"\",\"validatorName\":\"continuous_range\",\"params\":[\"start\",\"end\n" +
                " \"]}]";
        List<ValidationRule> validationRules = JSONObject.parseArray(rule, ValidationRule.class);
        for (ValidationRule validationRule : validationRules) {
            System.out.println(validationRule.getpName());
        }
    }
}
