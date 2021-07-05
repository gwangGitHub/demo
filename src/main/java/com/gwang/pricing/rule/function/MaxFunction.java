package com.gwang.pricing.rule.function;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.math.BigDecimal;
import java.util.Map;

public class MaxFunction extends AbstractVariadicFunction {
    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        Number max = 0;
        for(AviatorObject each : args){
            if(each.getValue(env) == null){
                continue;
            }
            Number value = FunctionUtils.getNumberValue(each, env);
            if (value.doubleValue() > max.doubleValue()) {
                max = value;
            }
        }
        return new AviatorDecimal(BigDecimal.valueOf(max.doubleValue()));
    }

    @Override
    public String getName() {
        return "max";
    }
}
