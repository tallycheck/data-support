package com.taoswork.tallycheck.dataservice.io.request;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by gaoyuan on 7/1/16.
 */
public class Request implements Serializable {
    /**
     * Entity type: basically interface/class name
     */
    protected String type;

    protected Locale locale;

    public Request() {
    }

    public Request(String type, Locale locale) {
        this.type = type;
        this.locale = locale;
    }

    public Request(Class type, Locale locale) {
        this.type = type.getName();
        this.locale = locale;
    }

    public Request(Request req) {
        this.type = req.type;
        this.locale = req.locale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setType(Class type) {
        this.type = type.getName();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setParameter(String type, Locale locale) {
        this.type = type;
        this.locale = locale;
    }
}
