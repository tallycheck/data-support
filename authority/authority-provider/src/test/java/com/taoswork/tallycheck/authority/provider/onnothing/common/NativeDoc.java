package com.taoswork.tallycheck.authority.provider.onnothing.common;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class NativeDoc extends NativeResource {
    private Set<String> tags = new HashSet<String>();

    public NativeDoc(String name, Collection<String> tags) {
        super(name);
        if (tags != null) {
            for (String tag : tags) {
                this.tags.add(tag);
            }
        }
    }

    public NativeDoc addTag(String tag) {
        tags.add(tag);
        return this;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String tag : tags) {
            sb.append(tag + ',');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NativeDoc nativeDoc = (NativeDoc) o;

        return !(tags != null ? !tags.equals(nativeDoc.tags) : nativeDoc.tags != null);

    }

    @Override
    public int hashCode() {
        return tags != null ? tags.hashCode() : 0;
    }
}
