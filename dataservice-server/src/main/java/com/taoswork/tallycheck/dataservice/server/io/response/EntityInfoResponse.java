package com.taoswork.tallycheck.dataservice.server.io.response;

import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/9/30.
 */
public class EntityInfoResponse extends EntityResponse {
    @Override
    public String getAction() {
        return EntityAction.INFO.getType();
    }
}
