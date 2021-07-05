package com.gwang.pricing.rule.validation;


import com.google.common.collect.Maps;
import com.gwang.pricing.rule.validation.validator.*;

import java.util.Map;

public class ValidatorFactory {
    private static final Map<String, Validator> validatorMap = Maps.newHashMap();
    static {
        validatorMap.put(ValidatorConstants.CONTINUOUS_RANGE, new ContainerContinuousRangeValidator());
        validatorMap.put(ValidatorConstants.UNIQUE_RANGE, new ContainerUniqueRangeValidator());
        validatorMap.put(ValidatorConstants.PRECISION, new InputPrecisionValidator());
        validatorMap.put(ValidatorConstants.UNIQUE_VALUE, new ContainerUniqueValueValidator());
        validatorMap.put(ValidatorConstants.GREATER_THAN, new GreaterValidator());
        validatorMap.put(ValidatorConstants.GREATER_THAN_OR_EQUAL, new GreaterOrEqualValidator());
        validatorMap.put(ValidatorConstants.LESS_THAN, new LessValidator());
        validatorMap.put(ValidatorConstants.LESS_THAN_OR_EQUAL, new LessOrEqualValidator());
        validatorMap.put(ValidatorConstants.NUMBER_EQUAL, new NumberEqualValidator());
        validatorMap.put(ValidatorConstants.STRING_EQUAL, new StringEqualValidator());
    }
    public static Validator getValidator(String validatorName) {
        return validatorMap.get(validatorName);
    }

}
