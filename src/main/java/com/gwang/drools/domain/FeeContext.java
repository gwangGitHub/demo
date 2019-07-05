package com.gwang.drools.domain;

import java.math.BigDecimal;

/**
 * Created by gangwang on 2019/7/3.
 */
public class FeeContext {

    private int distance;

    private BigDecimal distanceFee;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public BigDecimal getDistanceFee() {
        return distanceFee;
    }

    public void setDistanceFee(BigDecimal distanceFee) {
        this.distanceFee = distanceFee;
    }
}
