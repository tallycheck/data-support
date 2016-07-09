package com.taoswork.tallycheck.info.descriptor.base.impl;

import com.taoswork.tallycheck.info.descriptor.base.NamedInfo;
import com.taoswork.tallycheck.info.descriptor.base.NamedOrderedInfo;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class NamedOrderedInfoImpl
        extends NamedInfoImpl
        implements NamedOrderedInfo, NamedOrderedInfoRW {
    public int order;

    public NamedOrderedInfoImpl() {
        this("", "");
    }

    public NamedOrderedInfoImpl(String name, String friendlyName) {
        super(name, friendlyName);
    }

    public NamedOrderedInfoImpl(String name, String friendlyName, int order) {
        super(name, friendlyName);
        setOrder(order);
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public void copyNamedInfo(NamedInfo source) {
        super.copyNamedInfo(source);
        if (source instanceof NamedOrderedInfo) {
            this.order = ((NamedOrderedInfo) source).getOrder();
        }
    }
}
