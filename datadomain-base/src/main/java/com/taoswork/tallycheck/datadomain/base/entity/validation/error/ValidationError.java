package com.taoswork.tallycheck.datadomain.base.entity.validation.error;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Gao Yuan on 2015/10/4.
 */
public class ValidationError implements Serializable {
    private String code;
    private Object[] args;

    public ValidationError(String code, Object[] args) {
        this.code = code;
        this.args = args;
    }

    public ValidationError(String code) {
        this(code, null);
    }

    public String getCode() {
        return code;
    }

    public ValidationError setCode(String code) {
        this.code = code;
        return this;
    }

    public Object[] getArgs() {
        return args;
    }

    public ValidationError setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationError that = (ValidationError) o;

        if (!code.equals(that.code)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(args, that.args);

    }

    @Override
    public int hashCode() {
        int result = code.hashCode();
        result = 31 * result + (args != null ? Arrays.hashCode(args) : 0);
        return result;
    }
}
