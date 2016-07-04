package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.provider.permission.authorities.SimpleKAuthority;

/**
 * Created by Gao Yuan on 2016/2/28.
 */
public class SKAuthority extends SimpleKAuthority {
    private final String namespace;
    private final String owner;

    public SKAuthority(String namespace, String owner) {
        this.namespace = namespace;
        this.owner = owner;
    }
}
