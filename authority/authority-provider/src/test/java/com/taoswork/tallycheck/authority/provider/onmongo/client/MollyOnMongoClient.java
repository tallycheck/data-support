package com.taoswork.tallycheck.authority.provider.onmongo.client;

import com.taoswork.tallycheck.authority.client.filter.FilterManager;
import com.taoswork.tallycheck.authority.client.filter.FilterType;
import com.taoswork.tallycheck.authority.client.impl.AccessClientImpl;
import com.taoswork.tallycheck.authority.provider.AuthorityProvider;
import com.taoswork.tallycheck.authority.provider.onmongo.common.ClassifiedFilters;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.*;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class MollyOnMongoClient extends AccessClientImpl {
    public MollyOnMongoClient(AuthorityProvider provider) {
        super(provider, new FilterManager());
        this.filterManager
                .registerFilterMaker(
                        new FilterType(XFile.class, ClassifiedFilters.BY_CLASSIFY), FilterByClassifications.class)
                .registerFilterMaker(
                        new FilterType(CCFile.class, ClassifiedFilters.BY_CLASSIFY), FilterByClassifications.class)
                .registerFilterMaker(
                        new FilterType(CM0File.class, ClassifiedFilters.BY_CLASSIFY), FilterByClassifications.class)
                .registerFilterMaker(
                        new FilterType(CM1File.class, ClassifiedFilters.BY_CLASSIFY), FilterByClassifications.class)
                .registerFilterMaker(
                        new FilterType(CS0File.class, ClassifiedFilters.BY_CLASSIFY), FilterByClassifications.class)
                .registerFilterMaker(
                        new FilterType(CS1File.class, ClassifiedFilters.BY_CLASSIFY), FilterByClassifications.class)
        ;
    }
}
