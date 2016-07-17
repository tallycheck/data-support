package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.provider.permission.authorities.SimpleKAuthority;

/**
 * Created by Gao Yuan on 2016/2/28.
 */
public class SKAuthority extends SimpleKAuthority {
    private final String region;
    private final String owner;

    public SKAuthority(String region, String owner) {
        this.region = region;
        this.owner = owner;
    }
}
