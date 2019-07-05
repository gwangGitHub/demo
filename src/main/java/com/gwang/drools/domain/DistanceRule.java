package com.gwang.drools.domain;

import java.util.List;

/**
 * Created by gangwang on 2019/7/3.
 */
public class DistanceRule {

    private int defaultDistance;

    private List<RulePairCombination> rulePairs;

    public int getDefaultDistance() {
        return defaultDistance;
    }

    public void setDefaultDistance(int defaultDistance) {
        this.defaultDistance = defaultDistance;
    }

    public List<RulePairCombination> getRulePairs() {
        return rulePairs;
    }

    public void setRulePairs(List<RulePairCombination> rulePairs) {
        this.rulePairs = rulePairs;
    }
}
