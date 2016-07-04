package com.taoswork.tallycheck.authority.domain.user;

import org.mongodb.morphia.annotations.Entity;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
@Entity
public abstract class UserAuthority<GA extends GroupAuthority> extends BaseAuthority {
    public abstract Collection<? extends GroupAuthority> theGroups();
}
