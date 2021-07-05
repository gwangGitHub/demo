package com.gwang.pricing.rule.validation.validator;

public class NumberRange {
    private double start;
    private double end;

    public NumberRange(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }

    public boolean isIntersectWith(NumberRange another) {
        return (this.start >= another.getStart() && this.start < another.getEnd())
                || (this.end > another.getStart() && this.end <= another.getEnd())
                ||(this.start <= another.start && this.end >= another.end);
    }

}
