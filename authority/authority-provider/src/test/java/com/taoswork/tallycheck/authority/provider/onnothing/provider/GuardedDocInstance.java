package com.taoswork.tallycheck.authority.provider.onnothing.provider;

import com.taoswork.tallycheck.authority.provider.onnothing.common.NativeDoc;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class GuardedDocInstance implements Serializable {
    private final NativeDoc domainObject;

    public GuardedDocInstance(NativeDoc doc) {
        domainObject = doc;
    }

    public NativeDoc getDomainObject() {
        return domainObject;
    }

    @Override
    public String toString() {
        return "" + domainObject;
    }
}
