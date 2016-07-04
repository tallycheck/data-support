package com.taoswork.tallycheck.datasolution.security;

/**
 * Created by gaoyuan on 7/3/16.
 */
public final class EntityFilterType {
    public String resourceTypeName;
    public String filterName;

    public EntityFilterType(String resourceTypeName, String filterName) {
        this.resourceTypeName = resourceTypeName;
        this.filterName = filterName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntityFilterType that = (EntityFilterType) o;

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
