package com.taoswork.tallycheck.authority.provider.resource;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionLink;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Gao Yuan on 2015/8/22.
 */
public final class KResourceAccess {
    private final String resource;
    private final Access access;

    public KResourceAccess(String resource, Access access) {
        this.resource = resource;
        this.access = access;
    }

    public KResourceAccess(IKProtectionLink protection) {
        this(protection.getVirtualResource(), protection.getVirtualAccess());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof KResourceAccess)) return false;

        KResourceAccess that = (KResourceAccess) o;

        return new EqualsBuilder()
                .append(resource, that.resource)
                .append(access, that.access)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(resource)
                .append(access)
                .toHashCode();
    }

    @Override
    public String toString() {
        return "resource:'" + resource + '\'' +
                ", access:'" + access +
                '\'';
    }
}
