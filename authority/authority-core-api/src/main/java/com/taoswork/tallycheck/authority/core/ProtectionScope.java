package com.taoswork.tallycheck.authority.core;

import java.io.Serializable;

/**
 * Created by gaoyuan on 7/2/16.
 */
public final class ProtectionScope implements Serializable {
    public String spec;
    public String region;

    public ProtectionScope(String spec, String region) {
        this.spec = spec;
        this.region = region;
    }

    public ProtectionScope() {
    }
}
