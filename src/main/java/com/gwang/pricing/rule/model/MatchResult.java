package com.gwang.pricing.rule.model;

public class MatchResult {
    private boolean matched;
    private Object matchedData;

    public MatchResult() {
    }

    public MatchResult(boolean matched) {
        this.matched = matched;
        this.matchedData = null;
    }

    public MatchResult(boolean matched, Object matchedData) {
        this.matched = matched;
        this.matchedData = matchedData;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public Object getMatchedData() {
        return matchedData;
    }

    public void setMatchedData(Object matchedData) {
        this.matchedData = matchedData;
    }
}
