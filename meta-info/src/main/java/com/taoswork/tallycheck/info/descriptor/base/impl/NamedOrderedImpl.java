package com.taoswork.tallycheck.info.descriptor.base.impl;

import com.taoswork.tallycheck.info.descriptor.base.Named;
import com.taoswork.tallycheck.info.descriptor.base.NamedOrdered;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class NamedOrderedImpl
        extends NamedImpl
        implements NamedOrdered {
    public int order = 0;

    public NamedOrderedImpl() {
        this("", "");
    }

    public NamedOrderedImpl(String name, String friendlyName) {
        super(name, friendlyName);
    }

    public NamedOrderedImpl(String name, String friendlyName, int order) {
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
    public void copyNamedInfo(Named source) {
        super.copyNamedInfo(source);
        if (source instanceof NamedOrdered) {
            this.order = ((NamedOrdered) source).getOrder();
        }
    }
}
