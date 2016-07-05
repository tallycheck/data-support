package com.taoswork.tallycheck.authority.provider.onmongo.client;

import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.authority.client.filter.FilterType;
import com.taoswork.tallycheck.authority.client.impl.AuthorityVerifierImpl;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.authority.provider.onmongo.common.ClassifiedFilters;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.*;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class MollyOnMongoClient extends AuthorityVerifierImpl {
    public MollyOnMongoClient(IAuthorityProvider provider) {
        super(provider, new EntityFilterManager());
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
