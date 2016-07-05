package com.taoswork.tallycheck.authority.provider.onnothing.client;

import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.authority.client.filter.FilterType;
import com.taoswork.tallycheck.authority.client.impl.AuthorityVerifierImpl;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.authority.provider.onnothing.common.TypesEnums;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class MollyOnNothingClient extends AuthorityVerifierImpl {
    public MollyOnNothingClient(IAuthorityProvider provider) {
        super(provider, new EntityFilterManager());
        this.filterManager.registerFilterMaker(
                new FilterType(TypesEnums.DOC, ByTagFilter.TYPE_NAME), ByTagFilter.class);
    }
}
