package com.taoswork.tallycheck.dataservice.frontend.io.response;

import com.taoswork.tallycheck.datadomain.base.restful.EntityAction;

/**
 * Created by Gao Yuan on 2015/9/24.
 */
public class EntityCreateResponse extends EntityInstanceResponse {
    private final String createUri;
    private final String beanUri;

    public EntityCreateResponse(String createUri, String beanUri) {
        this.createUri = createUri;
        this.beanUri = beanUri;
    }

    public String getBeanUri() {
        return beanUri;
    }

    public String getCreateUri() {
        return createUri;
    }

    @Override
    public String getAction() {
        return EntityAction.CREATE.getType();
    }
}
