package com.gwang.test.testng.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * 单元测试基类
 *
 * @author gangwang
 */
@ContextConfiguration(locations = "classpath:testng.xml")
public abstract class TestNGBaseTest extends AbstractTestNGSpringContextTests {
    /**
     * 非自动化测试组名
     * <p>
     * 如果一个测试方法不能自动化运行，例如调用外部接口、调用 Redis 的测试方法，就需要将 {@link Test#groups()} 指
     * 定为该值，这样在执行 testng.xml 时，该方法就不会执行。
     */
    protected static final String NON_AUTOMATIC_TEST = "non-automatic-test";
}
