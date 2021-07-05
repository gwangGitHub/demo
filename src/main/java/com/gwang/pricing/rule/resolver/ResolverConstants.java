package com.gwang.pricing.rule.resolver;

public class ResolverConstants {

    public static final String RESOLVER_AND = "and";
    public static final String RESOLVER_OR = "or";
    public static final String RESOLVER_MULTI_SUM = "sum";
    public static final String RESOLVER_MULTI_MIN = "min";
    public static final String RESOLVER_MULTI_MAX = "max";

    public static final String RESOLVER_NUMBER_RANGE = "number_range";
    public static final String RESOLVER_NUMBER_STEP = "number_step";
    public static final String RESOLVER_INPUT_NUMBER_EQUAL = "number_equal";
    public static final String RESOLVER_INPUT_STRING_EQUAL = "string_equal";
    public static final String RESOLVER_INPUT_GRATER_THAN = "greater_than";
    public static final String RESOLVER_INPUT_GRATER_OR_EQUAL = "greater_or_equal";
    public static final String RESOLVER_INPUT_LESS_THAN = "less_than";
    public static final String RESOLVER_INPUT_LESS_OR_EQUAL = "less_or_equal";
    public static final String RESOLVER_INPUT_BOOLEAN_FACTOR = "boolean_factor";
    public static final String RESOLVER_INPUT_FIX_VALUE = "fix_value";
    public static final String RESOLVER_MULTI_VALUE = "multi_value";
    public static final String RESOLVER_TIME_RANGE = "time_range";
    public static final String RESOLVER_DATE_TIME_RANGE = "datetime_range";
    public static final String RESOLVER_FAST_MARKUP = "fast_markup";

    public static final String START = "start";
    public static final String END = "end";
    public static final String BASE_FEE = "baseFee";
    public static final String STEP = "step";
    public static final String STEP_FEE = "stepFee";
    public static final String VALUE = "value";


    public static final String DATA_TYPE = "type";
    public static final String DT_TYPE_TIME = "time";
    public static final String DT_TYPE_DATE_TIME = "datetime";
    public static final String DT_TYPE_DATE = "date";

    public static final String MULTI_MATCH = "multi_match";
    public static final String LEFT_CLOSE = "left_close";
    public static final String RIGHT_CLOSE = "right_close";

    //动态加价部分的常量
    public static final String NORMAL_START = "normalStart";
    public static final String PRE_BOOK_START = "prebookStart";
    public static final String PRE_BOOK_REMAIN = "prebookRemain";
    public static final String UP_LIMIT = "upLimit";
    public static final String IGNORE_WEATHER = "isIgnoreWeather";

    //雨天加价
    public static final Integer RAINY_MARKUP = 1;
    //动态加价运单属性部分
    public static final String IS_PRE_BOOK = "isPreBook";
    public static final String CALCULATE_TIME = "grabTime";
    public static final String DELIVERED_TIME = "deliveredTime";
    public static final String ACTION_TIME = "actionTime";
    public static final String IS_HAVING_WEATHER_SUBSIDY = "isHavingWeatherSubsidy";

    public static final String RESOLVER_INPUT_FACTOR_VALUE = "factor_value";

}
