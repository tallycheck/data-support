package com.taoswork.tallycheck.dataservice.io.response;

import java.io.Serializable;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class Response implements Serializable {
    protected boolean success;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
