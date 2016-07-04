package com.taoswork.tallycheck.authority.client.filter;

/**
 * Created by gaoyuan on 7/2/16.
 */
public interface IFilter {
    String getResource();

    void setResource(String resourceTypeName);

    String getFilterParameter();

    void setFilterParameter(String parameter);

    boolean isMatch(Object instance);
}
