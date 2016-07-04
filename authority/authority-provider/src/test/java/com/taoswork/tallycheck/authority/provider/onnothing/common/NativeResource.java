package com.taoswork.tallycheck.authority.provider.onnothing.common;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public abstract class NativeResource {
    private final String name;

    public NativeResource(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
