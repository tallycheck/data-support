package com.taoswork.tallycheck.authority.client.filter.maker;

import com.taoswork.tallycheck.authority.client.filter.IFilter;

/**
 * Created by gaoyuan on 7/2/16.
 */
public class ByClassFilterMaker implements IFilterMaker {
    private final Class<? extends IFilter> filterClz;

    public ByClassFilterMaker(Class<? extends IFilter> filterClz) {
        this.filterClz = filterClz;
    }

    @Override
    public IFilter make() {
        try {
            if (IFilter.class.isAssignableFrom(filterClz)) {
                IFilter filter = (IFilter) filterClz.newInstance();
                return filter;
            } else {
                return null;
            }
        } catch (InstantiationException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        }
    }
}
