package com.taoswork.tallycheck.info.descriptor.base.impl;

import com.taoswork.tallycheck.info.descriptor.base.Named;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public class NamedImpl implements Named {
    public String name;
    public String friendlyName;

    public NamedImpl() {
        this("", "");
    }

    public NamedImpl(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public NamedImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getFriendlyName() {
        return friendlyName;
    }

    @Override
    public NamedImpl setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
        return this;
    }

    public void copyNamedInfo(Named source) {
        this.name = source.getName();
        this.friendlyName = source.getFriendlyName();
    }
}
