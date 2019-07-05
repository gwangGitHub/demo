package com.gwang.drools.domain;

import java.util.List;

/**
 * Created by gangwang on 2019/7/3.
 */
public class DistanceRule {

    private int defaultDistance;

    private List<DistanceRulePair> rulePairs;

    public int getDefaultDistance() {
        return defaultDistance;
    }

    public void setDefaultDistance(int defaultDistance) {
        this.defaultDistance = defaultDistance;
    }

    public List<DistanceRulePair> getRulePairs() {
        return rulePairs;
    }

    public void setRulePairs(List<DistanceRulePair> rulePairs) {
        this.rulePairs = rulePairs;
    }

}
