package com.gwang.pricing.rule.function;

import com.googlecode.aviator.runtime.function.AbstractVariadicFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDecimal;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

public class FloorFunction extends AbstractVariadicFunction {
    @Override
    public AviatorObject variadicCall(Map<String, Object> env, AviatorObject... args) {
        Number value = FunctionUtils.getNumberValue(args[0], env);
        return new AviatorDecimal(Math.floor(value.doubleValue()));
    }

    @Override
    public String getName() {
        return "floor";
    }
}
