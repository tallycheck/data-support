package com.taoswork.tallycheck.dataservice.server.io.response;

import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/11/26.
 */
public class CollectionEntryCreateFreshResponse extends EntityCreateFreshResponse {
    public CollectionEntryCreateFreshResponse(String createUri) {
        super(createUri);
    }

    @Override
    public String getAction() {
        return EntityAction.CREATE.getType();
    }
}
