package com.gwang.pricing.rule.model;

import com.google.common.collect.Maps;

import java.util.Map;

public class PricingContext {
    private Map<String, Object> context = Maps.newHashMap();
    private Map<Long, String> factorCache = Maps.newHashMap();
    private Map<Long, String> resolverCache = Maps.newHashMap();

    //TODO 临时字段，用于灰度加盟封顶值逻辑
    private boolean calNewLogic = false;

    public void put(String key, Object value) {
        Object newValue = transObjectToRightType(key, value);
        context.put(key, newValue);
    }

    public Object getFactorValue(String key) {
//        String factorCode = factorCache.get(TypeUtils.castToLong(key));
        return context.get(key);
    }
    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public String getFactorName(Long factorId) {
        return factorCache.get(factorId);
    }

    public void setFactorCache(Map<Long, String> factorCache) {
        this.factorCache = factorCache;
    }

    public String getResolverName(Long resolverId) {
        return resolverCache.get(resolverId);
    }

    public void setResolverCache(Map<Long, String> resolverCache) {
        this.resolverCache = resolverCache;
    }

    private Object transObjectToRightType(String key, Object value) {
//        String factorType = FactorCache.getFactorValue(key).getDataType();
//        if("dateTime".equals(factorType)) {
//            return "";
//        }
//        if("date".equals(factorType)) {
//            return "";
//        }
        return value;
    }

    public boolean isCalNewLogic() {
        return calNewLogic;
    }

    public PricingContext setCalNewLogic(boolean calNewLogic) {
        this.calNewLogic = calNewLogic;
        return this;
    }
}
