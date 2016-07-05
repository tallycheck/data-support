package com.taoswork.tallycheck.authority.client.filter;


import com.taoswork.tallycheck.authority.client.filter.maker.ByClassFilterMaker;
import com.taoswork.tallycheck.authority.client.filter.maker.IFilterMaker;
import org.apache.commons.collections4.map.LRUMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
public class EntityFilterManager {

    public final static String COMPONENT_NAME = "EntityFilterManager";

    private final Map<FilterType, IFilterMaker> filterMakers = new HashMap<FilterType, IFilterMaker>();

    private final Map<String, IFilter> filterMap = new LRUMap<String, IFilter>(1000);

    public EntityFilterManager registerFilterMaker(FilterType filterType, IFilterMaker filterMaker){
        filterMakers.put(filterType, filterMaker);
        return this;
    }

    public EntityFilterManager registerFilterMaker(FilterType filterType, Class<? extends IFilter> filterClz){
        filterMakers.put(filterType, new ByClassFilterMaker(filterClz));
        return this;
    }

    public IFilter getFilter(String resource, String filterType, String parameter) {
        return getFilter(new FilterType(resource, filterType), parameter);
    }

    public IFilter getFilter(FilterType ft, String parameter) {
        String fm = calcFingerMark(ft, parameter);
        IFilter filter = filterMap.get(fm);
        if (filter == null) {
            IFilterMaker filterMaker = filterMakers.get(ft);
            if (filterMaker!=null) {
                filter = filterMaker.make();
                filter.setFilterParameter(parameter);
                filterMap.put(fm, filter);
            }
        }
        if(filter == null)
            throw new RuntimeException("Unexpected filter type");
        return filter;
    }

    protected static String calcFingerMark(FilterType ft,
                                        String filterParameter) {
        return ft.toString() + ":"+filterParameter;
    }

}