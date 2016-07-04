package com.taoswork.tallycheck.authority.provider.onnothing.client;

import com.taoswork.tallycheck.authority.client.filter.FilterManager;
import com.taoswork.tallycheck.authority.client.filter.FilterType;
import com.taoswork.tallycheck.authority.client.impl.AccessClientImpl;
import com.taoswork.tallycheck.authority.provider.onnothing.common.TypesEnums;
import com.taoswork.tallycheck.authority.provider.AuthorityProvider;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class MollyOnNothingClient extends AccessClientImpl {
    public MollyOnNothingClient(AuthorityProvider provider) {
        super(provider, new FilterManager());
        this.filterManager.registerFilterMaker(
                new FilterType(TypesEnums.DOC, ByTagFilter.TYPE_NAME), ByTagFilter.class);
    }
}
