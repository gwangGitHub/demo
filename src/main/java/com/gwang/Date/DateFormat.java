package com.gwang.Date;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateFormat {

    /**
     * 即将跨年，YYYY-MM-dd 的BUG 自查了吗？
     * 2021-12-29 17:22·双晨传奇科技
     * 在任何编程语言中，对于时间、数字等数据上，都存在很多类似这种平时一切OK，特定时间、特定环境出问题的情况。据了解，每年因为格式化时间用到了"YYYY-MM-dd"，元旦当天被喊回去改Bug，到底是怎么回事哪？
     *
     * YYYY 和 yyyy
     *
     * 即将跨年，YYYY-MM-dd 的BUG 自查了吗？
     * 输出结果：
     *
     * 2021-12-31 转 YYYY/MM/dd 格式: 2022/12/31
     *
     * 2022-01-01 转 YYYY/MM/dd 格式: 2022/01/01
     *
     * 2021-12-31 转 yyyy/MM/dd 格式: 2021/12/31
     *
     * 2022-01-01 转 yyyy/MM/dd 格式: 2022/01/01
     *
     * 细心的同学应该发现了2021-12-31用YYYY/MM/dd 此刻变成了2022/12/31
     *
     * 就是说如果2021年底买的东西，你如果用YYYY来格式化出库日期，是不是得到2022年底才能收到货？
     *
     * BUG 的原因
     *
     * 为什么YYYY-MM-dd格式化2021年12月31日的时候，会到2022年呢？
     *
     * YYYY 是 week-based-year，表示：当天所在的周属于的年份，一周从周日开始，周六结束，只要本周跨年，那么这周就算入下一年。就比如说今年(2021-2022) 12.31 这一周是跨年的第一周，而 12.31 是周五，那使用 YYYY 的话会显示 2022，使用 yyyy的时候，就还是 2021 年。
     * @param args
     */

    public static void main(String[] args) {

        //21年从26号（周日）开始算新的一周，22年1月1日周六，正好快年，所以从26号开始到31日都日期都会有问题
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2021, Calendar.DECEMBER, 25);
        Date date1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2021, Calendar.DECEMBER, 26);
        Date date2 = calendar2.getTime();

        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2022, Calendar.JANUARY, 1);
        Date date3 = calendar3.getTime();

        SimpleDateFormat formatYYYY = new SimpleDateFormat("YYYY/MM/dd");
        System.out.println(date1.toString() + " YYYY格式：" + formatYYYY.format(date1));
        System.out.println(date2.toString() + " YYYY格式：" + formatYYYY.format(date2));
        System.out.println(date2.toString() + " YYYY格式：" + formatYYYY.format(date3));

        SimpleDateFormat formatyyyy = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(date1.toString() + " yyyy格式：" + formatyyyy.format(date1));
        System.out.println(date2.toString() + " yyyy格式：" + formatyyyy.format(date2));
        System.out.println(date2.toString() + " yyyy格式：" + formatyyyy.format(date3));


        List<Long> oldList = new ArrayList<>();
        for (long i = 0; i < 5; i++) {
            oldList.add(Long.valueOf(i));
        }
        List<Long> newList = Lists.transform(oldList, new Function<Long, Long>() {
            public Long apply(Long input) {
                Long r = input;
                return r;
            }
        });

        System.out.println("oldList before change:"+ oldList);
        System.out.println("newList before change:"+ newList);

        for (int i = 0; i < oldList.size(); i++){
            Long oldValue = oldList.get(i);
            oldList.set(i,oldValue + 1L);
        }

        for (int i = 0; i < newList.size(); i++){
            Long newValue = oldList.get(i);
            oldList.set(i,newValue + 1L);
        }

        System.out.println("oldList after change1:"+ oldList);
        System.out.println("newList after change1:"+ newList);

        for (int i = 0; i < oldList.size(); i++){
            Long oldValue = oldList.get(i);
            oldList.set(i,oldValue + 1L);
        }

        for (int i = 0; i < newList.size(); i++){
            Long newValue = oldList.get(i);
            oldList.set(i,newValue + 1L);
        }

        System.out.println("oldList after change2:"+ oldList);
        System.out.println("newList after change2:"+ newList);
    }
}
