package com.gwang.drools.domain;

import java.math.BigDecimal;

/**
 * Created by gangwang on 2019/7/3.
 */
public class DistanceRulePair {
    private int startDistance;

    private int endDistance;

    private BigDecimal money;

    public DistanceRulePair(int startDistance, int endDistance, BigDecimal money) {
        this.startDistance = startDistance;
        this.endDistance = endDistance;
        this.money = money;
    }

    public DistanceRulePair(int startDistance, int endDistance) {
        this.startDistance = startDistance;
        this.endDistance = endDistance;
    }

    public int getStartDistance() {
        return startDistance;
    }

    public void setStartDistance(int startDistance) {
        this.startDistance = startDistance;
    }

    public int getEndDistance() {
        return endDistance;
    }

    public void setEndDistance(int endDistance) {
        this.endDistance = endDistance;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
