package com.taoswork.tallycheck.dataservice.io.request;

import com.taoswork.tallycheck.dataservice.InfoType;

import java.util.Locale;

/**
 * Created by gaoyuan on 7/4/16.
 */
public class InfoRequest extends Request {
    public InfoRequest(String type) {
        super(type);
    }

    public InfoRequest(Class type) {
        super(type);
    }

    public boolean withHierarchy;
    public InfoType infoType;
    public Locale locale;
}
