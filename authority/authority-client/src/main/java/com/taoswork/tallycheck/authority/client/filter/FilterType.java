package com.taoswork.tallycheck.authority.client.filter;

import com.taoswork.tallycheck.authority.atom.utility.ResourceUtility;

/**
 * Created by gaoyuan on 7/2/16.
 */
public final class FilterType {
    public final String resourceTypeName;
    public String filterName;

    public FilterType(String resourceTypeName, String filterName) {
        this.resourceTypeName = ResourceUtility.unifiedResourceName(resourceTypeName);
        this.filterName = filterName;
    }
    public FilterType(Class<?> resourceType, String filterName) {
        this(resourceType.getName(), filterName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterType that = (FilterType) o;

        if (filterName != null ? !filterName.equals(that.filterName) : that.filterName != null) return false;
        return !(resourceTypeName != null ? !resourceTypeName.equals(that.resourceTypeName) : that.resourceTypeName != null);

    }

    @Override
    public int hashCode() {
        int result = filterName != null ? filterName.hashCode() : 0;
        result = 31 * result + (resourceTypeName != null ? resourceTypeName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return resourceTypeName + ":"+ filterName;
    }
}
