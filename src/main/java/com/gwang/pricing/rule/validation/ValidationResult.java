package com.gwang.pricing.rule.validation;

import org.apache.commons.lang3.StringUtils;

public class ValidationResult {
    private int code = 0;
    private String message;

    public ValidationResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ValidationResult() {

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isValid() {
        return 0 == code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void merge(ValidationResult merge) {
        if(merge.code == 1) {
            this.code = 1;
            if(StringUtils.isNotBlank(this.message )) {
                this.message = message + "|" + merge.message;
            } else {
                this.message = merge.message;
            }

        }
    }
}
