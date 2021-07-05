package com.gwang.test.testng.pricing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwang.pricing.rule.model.PricingContext;
import com.gwang.pricing.rule.model.Rule;
import com.gwang.pricing.rule.resolver.ResolverConstants;
import com.gwang.test.testng.pricing.common.PriceParamEnum;
import com.gwang.test.testng.pricing.model.RuleDataDetail;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class ResolverTest {

    private static String ruleTemple = "{\n" +
            "    \"c\":[\n" +
            "        {\n" +
            "            \"c\":[\n" +
            "                {\n" +
            "                    \"c\":[\n" +
            "                        {\n" +
            "                            \"f\":\"grabTime\",\n" +
            "                            \"r\":\"greater_or_equal\",\n" +
            "                            \"n\":\"start\",\n" +
            "                            \"rp\":{\n" +
            "                                \"ia\":false,\n" +
            "                                \"ir\":true\n" +
            "                            }\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"f\":\"grabTime\",\n" +
            "                            \"r\":\"less_than\",\n" +
            "                            \"n\":\"end\",\n" +
            "                            \"rp\":{\n" +
            "                                \"ia\":false,\n" +
            "                                \"ir\":true\n" +
            "                            }\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"r\":\"fix_value\",\n" +
            "                            \"n\":\"baseFee\",\n" +
            "                            \"rp\":{\n" +
            "                                \"ia\":false,\n" +
            "                                \"ir\":true\n" +
            "                            }\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"r\":\"and\",\n" +
            "                    \"n\":\"row\",\n" +
            "                    \"rp\":{\n" +
            "                        \"ia\":false,\n" +
            "                        \"ir\":true\n" +
            "                    }\n" +
            "                }\n" +
            "            ],\n" +
            "            \"r\":\"or\",\n" +
            "            \"n\":\"rules\",\n" +
            "            \"rp\":{\n" +
            "                \"ia\":true,\n" +
            "                \"ir\":true\n" +
            "            }\n" +
            "        }\n" +
            "    ],\n" +
            "    \"n\":\"global\",\n" +
            "    \"rp\":{\n" +
            "        \"ia\":false,\n" +
            "        \"ir\":true\n" +
            "    },\n" +
            "    \"r\":\"and\"\n" +
            "}";

    private static String ruleData = "{\"c\":[{\"d\":{\"type\":\"time\",\"value\":\"14:00\"},\"f\":\"grabTime\",\"matchedChildren\":[],\"r\":\"greater_or_equal\",\"resolver\":{}},{\"d\":{\"type\":\"time\",\"value\":\"16:00\"},\"f\":\"grabTime\",\"matchedChildren\":[],\"r\":\"less_than\",\"resolver\":{}}],\"matchedChildren\":[],\"r\":\"and\",\"resolver\":{}}\n";

    /**
     * 上线文初始化
     *
     * @return
     */
    private PricingContext initPricingContext() {
        PricingContext ctx = new PricingContext();
        Map<String, Object> context = new HashMap<>();
        ctx.setContext(context);
        return ctx;
    }

    /**
     * 规则初始化
     *
     * @return
     */
    private Rule initRule() {
        Rule rule = new Rule();
        return rule;
    }

    @Test(dataProvider = "inputGreaterEqualResolverProvider")
    public void inputGreaterEqualResolverTest(PricingContext ctx, Rule rule, boolean expectResult){
        System.out.println("resolver:" + rule.getResolverKey());
        System.out.println("ctx-" + rule.getFactorKey() + ":" + ctx.getContext().get(rule.getFactorKey()));
        System.out.println(JSON.toJSONString(rule));
        System.out.println(JSON.toJSONString(rule.getData()));
        boolean result = rule.match(ctx);
        System.out.println("result:" + result);
        assertEquals(result, expectResult);
    }

    /**
     * 该注解配合 @Test(dataProvider = "xxx") 用于参数化测试——给某一个单元测试方法传多组参数值验证方法对多组参数的处理是否正确
     * @return
     */
    @DataProvider
    private Object[][] inputGreaterEqualResolverProvider() {
        Object[][] timeMatchExample = timeMatchExample();
        Object[][] dateTimeMatchExample = dateTimeMatchExample();
        Object[][] dateMatchExample = dateMatchExample();
        Object[][] distanceMatchExample = distanceMatchExample();

        Object[][] all = new Object[][]{};
        all = ArrayUtils.addAll(all, timeMatchExample);
        all = ArrayUtils.addAll(all, dateTimeMatchExample);
        all = ArrayUtils.addAll(all, dateMatchExample);
        all = ArrayUtils.addAll(all, distanceMatchExample);

        //根据传参的个数决定测试用例跑的次数
//        return new Object[][]{{ctx1, rule1}}; //跑一次测试用例
//        return new Object[][]{{ctx1, rule1, expectResult1}, {ctx2, rule1, expectResult2}}; //跑两次测试用例
        return all;
    }

    /**
     * 测试时间命中
     * @return
     */
    private Object[][] timeMatchExample() {
        Rule timeRule= initRule();
        //定价方案里设置的时间
        timeRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        timeRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail timeDetail = new RuleDataDetail();
        timeDetail.setType(ResolverConstants.DT_TYPE_TIME);
        timeDetail.setValue("14:00");
        timeRule.setData(JSONObject.toJSON(timeDetail));


        //场景一：测试时间大于命中
        PricingContext timeCtx1 = initPricingContext();
        //定价时间 2021-05-21 14:20:00 --> 1621578000
        timeCtx1.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621578000);
        boolean expectResult1 = true;

        //场景二：测试时间等于命中
        PricingContext timeCtx2 = initPricingContext();
        //定价时间 2021-05-21 14:00:00 --> 1621576800
        timeCtx2.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621576800);
        boolean expectResult2 = true;

        //场景三：测试时间小于不命中
        PricingContext timeCtx3 = initPricingContext();
        //定价时间 2021-05-21 13:59:59 --> 1621576800
        timeCtx3.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621576799);
        boolean expectResult3 = false;
        return new Object[][] {{timeCtx1, timeRule, expectResult1}, {timeCtx2, timeRule, expectResult2}, {timeCtx3, timeRule, expectResult3}};
    }

    /**
     * 测试日期时间命中
     * @return
     */
    private Object[][] dateTimeMatchExample() {
        Rule dateTimeRule= initRule();
        //定价方案里设置的时间
        dateTimeRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        dateTimeRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail dateTimeDetail = new RuleDataDetail();
        dateTimeDetail.setType(ResolverConstants.DT_TYPE_DATE_TIME);
        dateTimeDetail.setValue("2021-05-21 14:00:00");
        dateTimeRule.setData(JSONObject.toJSON(dateTimeDetail));


        //场景一：测试时间大于命中
        PricingContext dateTimeCtx1 = initPricingContext();
        //定价时间 2021-05-21 14:20:00 --> 1621578000
        dateTimeCtx1.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621578000);
        boolean expectResult1 = true;

        //场景二：测试时间等于命中
        PricingContext dateTimeCtx2 = initPricingContext();
        //定价时间 2021-05-21 14:00:00 --> 1621576800
        dateTimeCtx2.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621576800);
        boolean expectResult2 = true;

        //场景三：测试时间小于不命中
        PricingContext dateTimeCtx3 = initPricingContext();
        //定价时间 2021-05-21 13:59:59 --> 1621576800
        dateTimeCtx3.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621576799);
        boolean expectResult3 = false;
        return new Object[][] {{dateTimeCtx1, dateTimeRule, expectResult1}, {dateTimeCtx2, dateTimeRule, expectResult2}, {dateTimeCtx3, dateTimeRule, expectResult3}};
    }

    /**
     * 测试日期命中
     * @return
     */
    private Object[][] dateMatchExample() {
        Rule dateRule= initRule();
        //定价方案里设置的时间
        dateRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        dateRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail dateTimeDetail = new RuleDataDetail();
        dateTimeDetail.setType(ResolverConstants.DT_TYPE_DATE);
        dateTimeDetail.setValue("2021-05-21");
        dateRule.setData(JSONObject.toJSON(dateTimeDetail));


        //场景一：测试日期大于命中
        PricingContext dateCtx1 = initPricingContext();
        //定价时间 2021-05-22 00:00:00 --> 1621578000
        dateCtx1.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621612800);
        boolean expectResult1 = true;

        //场景二：测试日期等于命中
        PricingContext dateCtx2 = initPricingContext();
        //定价时间 2021-05-21 00:00:00 --> 1621576800
        dateCtx2.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621526400);
        boolean expectResult2 = true;

        //场景三：测试时间小于不命中
        PricingContext dateCtx3 = initPricingContext();
        //定价时间 2021-05-20 00:00:00 --> 1621490400
        dateCtx3.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621490400);
        boolean expectResult3 = false;
        return new Object[][] {{dateCtx1, dateRule, expectResult1}, {dateCtx2, dateRule, expectResult2}, {dateCtx3, dateRule, expectResult3}};
    }

    /**
     * 测试数字命中(以距离为例)
     * @return
     */
    private Object[][] distanceMatchExample() {
        Rule distanceRule= initRule();
        //定价方案里设置的时间
        distanceRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        distanceRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail distanceDetail = new RuleDataDetail();
        distanceDetail.setValue("1000");
        distanceRule.setData(JSONObject.toJSON(distanceDetail));


        //场景一：测试距离大于命中
        PricingContext distanceCtx1 = initPricingContext();
        //定价距离999
        distanceCtx1.getContext().put(PriceParamEnum.DISTANCE.getCode(), 1001);
        boolean expectResult1 = true;

        //场景二：测试距离等于命中
        PricingContext distanceCtx2 = initPricingContext();
        //定价距离1000
        distanceCtx2.getContext().put(PriceParamEnum.DISTANCE.getCode(), 1000);
        boolean expectResult2 = true;

        //场景三：测试距离小于不命中
        PricingContext distanceCtx3 = initPricingContext();
        //定价距离999
        distanceCtx3.getContext().put(PriceParamEnum.DISTANCE.getCode(), 999);
        boolean expectResult3 = false;
        return new Object[][] {{distanceCtx1, distanceRule, expectResult1}, {distanceCtx2, distanceRule, expectResult2}, {distanceCtx3, distanceRule, expectResult3}};
    }

    @Test(dataProvider = "inputFixValueResolverProvider")
    public void inputFixValueResolverTest(PricingContext ctx, Rule rule, boolean expectResult){
        System.out.println("resolver:" + rule.getResolverKey());
        System.out.println("ctx-" + rule.getFactorKey() + ":" + ctx.getContext().get(rule.getFactorKey()));
        System.out.println(JSON.toJSONString(rule));
        System.out.println(JSON.toJSONString(rule.getData()));
        boolean result = rule.match(ctx);
        System.out.println("result:" + result);
        assertEquals(result, expectResult);
    }

    @DataProvider
    private Object[][] inputFixValueResolverProvider() {
        Object[][] fixValueMatchExample = fixValueMatchExample();

        Object[][] all = new Object[][]{};
        all = ArrayUtils.addAll(all, fixValueMatchExample);

        return all;
    }

    /**
     * 测试固定值命中，和具体因子值没关，都可以命中
     * @return
     */
    private Object[][] fixValueMatchExample() {
        //场景一：测试距离命中
        Rule distanceRule= initRule();
        //定价方案里设置的距离
        distanceRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_FIX_VALUE);
        distanceRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail distanceDetail = new RuleDataDetail();
        distanceDetail.setValue("1000");
        distanceRule.setData(JSONObject.toJSON(distanceDetail));


        PricingContext distanceCtx1 = initPricingContext();
        distanceCtx1.getContext().put(PriceParamEnum.DISTANCE.getCode(), 1001);
        boolean expectResult1 = true;

        //场景二：测试时间命中
        Rule timeRule= initRule();
        //定价方案里设置的时间
        timeRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_FIX_VALUE);
        timeRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail timeDetail = new RuleDataDetail();
        timeDetail.setType(ResolverConstants.DT_TYPE_TIME);
        timeDetail.setValue("14:00");
        timeRule.setData(JSONObject.toJSON(timeDetail));


        PricingContext timeCtx1 = initPricingContext();
        //定价时间 2021-05-21 14:20:00 --> 1621578000
        timeCtx1.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621578000);
        boolean expectResult2 = true;


        //场景三：测试日期时间命中
        Rule dateTimeRule= initRule();
        //定价方案里设置的时间
        dateTimeRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_FIX_VALUE);
        dateTimeRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail dateTimeDetail = new RuleDataDetail();
        dateTimeDetail.setType(ResolverConstants.DT_TYPE_DATE_TIME);
        dateTimeDetail.setValue("2021-05-21 14:00:00");
        dateTimeRule.setData(JSONObject.toJSON(dateTimeDetail));


        PricingContext dateTimeCtx1 = initPricingContext();
        //定价时间 2021-05-21 14:20:00 --> 1621578000
        dateTimeCtx1.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621578000);
        boolean expectResult3 = true;

        return new Object[][] {{distanceCtx1, distanceRule, expectResult1}, {timeCtx1, timeRule, expectResult2}, {dateTimeCtx1, dateTimeRule, expectResult3}};
    }

    @Test(dataProvider = "multiEqualResolverProvider")
    public void multiEqualResolverTest(PricingContext ctx, Rule rule, boolean expectResult){
        System.out.println("resolver:" + rule.getResolverKey());
        System.out.println("ctx-" + rule.getFactorKey() + ":" + ctx.getContext().get(rule.getFactorKey()));
        System.out.println(JSON.toJSONString(rule));
        System.out.println(JSON.toJSONString(rule.getData()));
        boolean result = rule.match(ctx);
        System.out.println("result:" + result);
        assertEquals(result, expectResult);
    }

    /**
     * 该注解配合 @Test(dataProvider = "xxx") 用于参数化测试——给某一个单元测试方法传多组参数值验证方法对多组参数的处理是否正确
     * @return
     */
    @DataProvider
    private Object[][] multiEqualResolverProvider() {
        Object[][] categoryMatchExample = categoryMatchExample();

        Object[][] all = new Object[][]{};
        all = ArrayUtils.addAll(all, categoryMatchExample);
        return all;
    }

    /**
     * 测试品类命中
     * @return
     */
    private Object[][] categoryMatchExample() {
        Rule categoryRule= initRule();
        //定价方案里设置的品类
        categoryRule.setResolverKey(ResolverConstants.RESOLVER_MULTI_VALUE);
        categoryRule.setFactorKey(PriceParamEnum.CATEGORY.getCode());
        RuleDataDetail categoryDetail = new RuleDataDetail();
        categoryDetail.setValue("17010000,10010000,10020000,11010000,11020000,11030000,11040000,11050000,11060000," +
                "12010000,13010000,13020000,13030000,14010000,14020000,14030000,14040000,14050000,14060000,14070000," +
                "14080000,15020000,15030000,15040000");
        categoryRule.setData(JSONObject.toJSON(categoryDetail));


        //场景一：测试品类命中第一个
        PricingContext categoryCtx1 = initPricingContext();
        //订单品类17010000
        categoryCtx1.getContext().put(PriceParamEnum.CATEGORY.getCode(), "17010000");
        boolean expectResult1 = true;

        //场景二：测试品类命中最后一个
        PricingContext categoryCtx2 = initPricingContext();
        //订单品类15040000
        categoryCtx2.getContext().put(PriceParamEnum.CATEGORY.getCode(), "15040000");
        boolean expectResult2 = true;

        //场景三：测试品类命中中间的一个
        PricingContext categoryCtx3 = initPricingContext();
        //订单品类14020000
        categoryCtx3.getContext().put(PriceParamEnum.CATEGORY.getCode(), "14020000");
        boolean expectResult3 = true;

        //场景四：测试品类不命中
        PricingContext categoryCtx4 = initPricingContext();
        //订单品类123456(不存在)
        categoryCtx4.getContext().put(PriceParamEnum.CATEGORY.getCode(), "123456");
        boolean expectResult4 = false;

        //场景五：测试品类为空不命中
        PricingContext categoryCtx5 = initPricingContext();
        //订单品类没有设置，没有值
        boolean expectResult5 = false;

        //场景六：测试品类为""空字符串不命中
        PricingContext categoryCtx6 = initPricingContext();
        //订单品类空字符串
        categoryCtx6.getContext().put(PriceParamEnum.CATEGORY.getCode(), "");
        boolean expectResult6 = false;

        //场景七：测试品类为"  "多个空格不命中
        PricingContext categoryCtx7 = initPricingContext();
        //订单品类空字符串
        categoryCtx7.getContext().put(PriceParamEnum.CATEGORY.getCode(), "  ");
        boolean expectResult7 = false;

        //场景八：测试品类为" 17010000 "真实品类多些空格不命中
        PricingContext categoryCtx8 = initPricingContext();
        //订单品类空字符串
        categoryCtx8.getContext().put(PriceParamEnum.CATEGORY.getCode(), " 17010000 ");
        boolean expectResult8 = false;


        return new Object[][] {{categoryCtx1, categoryRule, expectResult1}, {categoryCtx2, categoryRule, expectResult2},
                {categoryCtx3, categoryRule, expectResult3}, {categoryCtx4, categoryRule, expectResult4},
                {categoryCtx5, categoryRule, expectResult5}, {categoryCtx6, categoryRule, expectResult6},
                {categoryCtx7, categoryRule, expectResult7}, {categoryCtx8, categoryRule, expectResult8}, };
    }


    @Test(dataProvider = "andResolverProvider")
    public void andResolverTest(PricingContext ctx, Rule rule, boolean expectResult){
        System.out.println("resolver:" + rule.getResolverKey());
        System.out.println("ctx-" + rule.getFactorKey() + ":" + ctx.getContext().get(rule.getFactorKey()));
        System.out.println(JSON.toJSONString(rule));
        System.out.println(JSON.toJSONString(rule.getData()));
        boolean result = rule.match(ctx);
        System.out.println("result:" + result);
        assertEquals(result, expectResult);
    }

    /**
     * 该注解配合 @Test(dataProvider = "xxx") 用于参数化测试——给某一个单元测试方法传多组参数值验证方法对多组参数的处理是否正确
     * @return
     */
    @DataProvider
    private Object[][] andResolverProvider() {
        Object[][] timeAndMatchExample = timeAndMatchExample();
        Object[][] distanceeAndMatchExample = distanceeAndMatchExample();
        Object[][] categoryAndMatchExample = categoryAndMatchExample();

        Object[][] all = new Object[][]{};
        all = ArrayUtils.addAll(all, timeAndMatchExample);
        all = ArrayUtils.addAll(all, distanceeAndMatchExample);
        all = ArrayUtils.addAll(all, categoryAndMatchExample);
        return all;
    }

    /**
     * 测试时间命中
     * @return
     */
    private Object[][] timeAndMatchExample() {
        //父规则
        Rule andRule = initRule();
        andRule.setResolverKey(ResolverConstants.RESOLVER_AND);
        List<Rule> childRules = new ArrayList<>();
        andRule.setChildren(childRules);

        //子规则
        Rule greaterEqualRule = initRule();
        greaterEqualRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        greaterEqualRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail startTimeDetail = new RuleDataDetail();
        startTimeDetail.setType(ResolverConstants.DT_TYPE_TIME);
        startTimeDetail.setValue("14:00");
        greaterEqualRule.setData(JSONObject.toJSON(startTimeDetail));

        Rule lessRule = initRule();
        lessRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_LESS_THAN);
        lessRule.setFactorKey(PriceParamEnum.GRAB_TIME.getCode());
        RuleDataDetail endTimeDetail = new RuleDataDetail();
        endTimeDetail.setType(ResolverConstants.DT_TYPE_TIME);
        endTimeDetail.setValue("16:00");
        lessRule.setData(JSONObject.toJSON(endTimeDetail));

        childRules.add(greaterEqualRule);
        childRules.add(lessRule);

        //场景一：测试时间介于14：00到16：00之间，命中
        PricingContext timeCtx1 = initPricingContext();
        //定价时间 2021-05-21 14:20:00 --> 1621578000
        timeCtx1.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621578000);
        boolean expectResult1 = true;

        //场景二：测试时间等于14：00，命中
        PricingContext timeCtx2 = initPricingContext();
        //定价时间 2021-05-21 14:00:00 --> 1621576800
        timeCtx2.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621576800);
        boolean expectResult2 = true;

        //场景三：测试时间等于16：00，不命中
        PricingContext timeCtx3 = initPricingContext();
        //定价时间 2021-05-21 16:00:00 --> 1621584000
        timeCtx3.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621584000);
        boolean expectResult3 = false;

        //场景四：测试时间小于14：00，不命中
        PricingContext timeCtx4 = initPricingContext();
        //定价时间 2021-05-21 13:59:59 --> 1621576800
        timeCtx4.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621576799);
        boolean expectResult4 = false;

        //场景五：测试时间大于16：00，不命中
        PricingContext timeCtx5 = initPricingContext();
        //定价时间 2021-05-21 16:00:01 --> 1621576800
        timeCtx5.getContext().put(PriceParamEnum.GRAB_TIME.getCode(), 1621584001);
        boolean expectResult5 = false;


        return new Object[][] {{timeCtx1, andRule, expectResult1}, {timeCtx2, andRule, expectResult2},
                {timeCtx3, andRule, expectResult3}, {timeCtx4, andRule, expectResult4},
                {timeCtx5, andRule, expectResult5} };
    }

    /**
     * 测试时间命中
     * @return
     */
    private Object[][] distanceeAndMatchExample() {
        //父规则
        Rule andRule = initRule();
        andRule.setResolverKey(ResolverConstants.RESOLVER_AND);
        List<Rule> childRules = new ArrayList<>();
        andRule.setChildren(childRules);

        //子规则
        Rule greaterEqualRule = initRule();
        greaterEqualRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        greaterEqualRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail startDistanceDetail = new RuleDataDetail();
        startDistanceDetail.setValue("1000");
        greaterEqualRule.setData(JSONObject.toJSON(startDistanceDetail));

        Rule lessRule = initRule();
        lessRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_LESS_THAN);
        lessRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail endTimeDetail = new RuleDataDetail();
        endTimeDetail.setValue("2500");
        lessRule.setData(JSONObject.toJSON(endTimeDetail));

        childRules.add(greaterEqualRule);
        childRules.add(lessRule);

        //场景一：测试距离介于1000到2500之间，命中
        PricingContext distanceCtx1 = initPricingContext();
        //距离2200
        distanceCtx1.getContext().put(PriceParamEnum.DISTANCE.getCode(), 2200);
        boolean expectResult1 = true;

        //场景二：测试距离等于1000命中
        PricingContext distanceCtx2 = initPricingContext();
        //定价距离1000
        distanceCtx2.getContext().put(PriceParamEnum.DISTANCE.getCode(), 1000);
        boolean expectResult2 = true;

        //场景三：测试距离等于2500，不命中
        PricingContext distanceCtx3 = initPricingContext();
        //定价距离2500
        distanceCtx3.getContext().put(PriceParamEnum.DISTANCE.getCode(), 2500);
        boolean expectResult3 = false;

        //场景四：测试距离小于1000，不命中
        PricingContext distanceCtx4 = initPricingContext();
        //定价距离999
        distanceCtx4.getContext().put(PriceParamEnum.DISTANCE.getCode(), 999);
        boolean expectResult4 = false;

        //场景五：测试距离大于2500，不命中
        PricingContext distanceCtx5 = initPricingContext();
        //定价距离2501
        distanceCtx5.getContext().put(PriceParamEnum.DISTANCE.getCode(), 2501);
        boolean expectResult5 = false;


        return new Object[][] {{distanceCtx1, andRule, expectResult1}, {distanceCtx2, andRule, expectResult2},
                {distanceCtx3, andRule, expectResult3}, {distanceCtx4, andRule, expectResult4},
                {distanceCtx5, andRule, expectResult5} };
    }

    /**
     * 测试品类命中
     * @return
     */
    private Object[][] categoryAndMatchExample() {
        //父规则
        Rule andRule = initRule();
        andRule.setResolverKey(ResolverConstants.RESOLVER_AND);
        List<Rule> childRules = new ArrayList<>();
        andRule.setChildren(childRules);

        //子规则
        Rule categoryRule= initRule();
        //定价方案里设置的品类
        categoryRule.setResolverKey(ResolverConstants.RESOLVER_MULTI_VALUE);
        categoryRule.setFactorKey(PriceParamEnum.CATEGORY.getCode());
        RuleDataDetail categoryDetail = new RuleDataDetail();
        categoryDetail.setValue("17010000,10010000,10020000,11010000,11020000,11030000,11040000,11050000,11060000," +
                "12010000,13010000,13020000,13030000,14010000,14020000,14030000,14040000,14050000,14060000,14070000," +
                "14080000,15020000,15030000,15040000");
        categoryRule.setData(JSONObject.toJSON(categoryDetail));

        Rule fixRule= initRule();
        //定价方案里设置的距离
        fixRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_FIX_VALUE);
        fixRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail distanceDetail = new RuleDataDetail();
        distanceDetail.setValue("1000");
        fixRule.setData(JSONObject.toJSON(distanceDetail));

        childRules.add(categoryRule);
        childRules.add(fixRule);

        //场景一：测试品类命中第一个
        PricingContext categoryCtx1 = initPricingContext();
        //订单品类17010000
        categoryCtx1.getContext().put(PriceParamEnum.CATEGORY.getCode(), "17010000");
        boolean expectResult1 = true;

        //场景二：测试品类命中最后一个
        PricingContext categoryCtx2 = initPricingContext();
        //订单品类15040000
        categoryCtx2.getContext().put(PriceParamEnum.CATEGORY.getCode(), "15040000");
        boolean expectResult2 = true;

        //场景三：测试品类命中中间的一个
        PricingContext categoryCtx3 = initPricingContext();
        //订单品类14020000
        categoryCtx3.getContext().put(PriceParamEnum.CATEGORY.getCode(), "14020000");
        boolean expectResult3 = true;

        //场景四：测试品类不命中
        PricingContext categoryCtx4 = initPricingContext();
        //订单品类123456(不存在)
        categoryCtx4.getContext().put(PriceParamEnum.CATEGORY.getCode(), "123456");
        boolean expectResult4 = false;

        //场景五：测试品类为空不命中
        PricingContext categoryCtx5 = initPricingContext();
        //订单品类没有设置，没有值
        boolean expectResult5 = false;

        //场景六：测试品类为""空字符串不命中
        PricingContext categoryCtx6 = initPricingContext();
        //订单品类空字符串
        categoryCtx6.getContext().put(PriceParamEnum.CATEGORY.getCode(), "");
        boolean expectResult6 = false;

        //场景七：测试品类为"  "多个空格不命中
        PricingContext categoryCtx7 = initPricingContext();
        //订单品类空字符串
        categoryCtx7.getContext().put(PriceParamEnum.CATEGORY.getCode(), "  ");
        boolean expectResult7 = false;

        //场景八：测试品类为" 17010000 "真实品类多些空格不命中
        PricingContext categoryCtx8 = initPricingContext();
        //订单品类空字符串
        categoryCtx8.getContext().put(PriceParamEnum.CATEGORY.getCode(), " 17010000 ");
        boolean expectResult8 = false;


        return new Object[][] {{categoryCtx1, categoryRule, expectResult1}, {categoryCtx2, categoryRule, expectResult2},
                {categoryCtx3, categoryRule, expectResult3}, {categoryCtx4, categoryRule, expectResult4},
                {categoryCtx5, categoryRule, expectResult5}, {categoryCtx6, categoryRule, expectResult6},
                {categoryCtx7, categoryRule, expectResult7}, {categoryCtx8, categoryRule, expectResult8}, };
    }

    /**
     * 测试时间和距离都命中
     * @return
     */
    private Object[][] timeAnddistanceeMatchExample() {
        //父规则
        Rule andRule = initRule();
        andRule.setResolverKey(ResolverConstants.RESOLVER_AND);
        List<Rule> childRules = new ArrayList<>();
        andRule.setChildren(childRules);

        //时间匹配规则
        Rule timeRule = initRule();
        timeRule.setResolverKey(ResolverConstants.RESOLVER_AND);
        List<Rule> timeChildRules = new ArrayList<>();
        timeRule.setChildren(timeChildRules);


        //距离匹配规则
        Rule distanceRule = initRule();
        distanceRule.setResolverKey(ResolverConstants.RESOLVER_AND);
        List<Rule> distanceChildRules = new ArrayList<>();
        distanceRule.setChildren(distanceChildRules);

        //距离子规则
        Rule greaterEqualRule = initRule();
        greaterEqualRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_GRATER_OR_EQUAL);
        greaterEqualRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail startDistanceDetail = new RuleDataDetail();
        startDistanceDetail.setValue("1000");
        greaterEqualRule.setData(JSONObject.toJSON(startDistanceDetail));

        Rule lessRule = initRule();
        lessRule.setResolverKey(ResolverConstants.RESOLVER_INPUT_LESS_THAN);
        lessRule.setFactorKey(PriceParamEnum.DISTANCE.getCode());
        RuleDataDetail endTimeDetail = new RuleDataDetail();
        endTimeDetail.setValue("2500");
        lessRule.setData(JSONObject.toJSON(endTimeDetail));

        distanceChildRules.add(greaterEqualRule);
        distanceChildRules.add(lessRule);

        childRules.add(timeRule);
        childRules.add(distanceRule);



        //场景一：测试距离介于1000到2500之间，命中
        PricingContext distanceCtx1 = initPricingContext();
        //距离2200
        distanceCtx1.getContext().put(PriceParamEnum.DISTANCE.getCode(), 2200);
        boolean expectResult1 = true;

        //场景二：测试距离等于1000命中
        PricingContext distanceCtx2 = initPricingContext();
        //定价距离1000
        distanceCtx2.getContext().put(PriceParamEnum.DISTANCE.getCode(), 1000);
        boolean expectResult2 = true;

        //场景三：测试距离等于2500，不命中
        PricingContext distanceCtx3 = initPricingContext();
        //定价距离2500
        distanceCtx3.getContext().put(PriceParamEnum.DISTANCE.getCode(), 2500);
        boolean expectResult3 = false;

        //场景四：测试距离小于1000，不命中
        PricingContext distanceCtx4 = initPricingContext();
        //定价距离999
        distanceCtx4.getContext().put(PriceParamEnum.DISTANCE.getCode(), 999);
        boolean expectResult4 = false;

        //场景五：测试距离大于2500，不命中
        PricingContext distanceCtx5 = initPricingContext();
        //定价距离2501
        distanceCtx5.getContext().put(PriceParamEnum.DISTANCE.getCode(), 2501);
        boolean expectResult5 = false;


        return new Object[][] {{distanceCtx1, andRule, expectResult1}, {distanceCtx2, andRule, expectResult2},
                {distanceCtx3, andRule, expectResult3}, {distanceCtx4, andRule, expectResult4},
                {distanceCtx5, andRule, expectResult5} };
    }
}
