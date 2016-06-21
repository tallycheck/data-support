package com.taoswork.tallycheck.authority.core.resource.link;

import com.taoswork.tallycheck.authority.core.resource.KResourceAccess;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public interface IKProtectionMapping {
    /**
     * Adjust the name of resourceEntity. (consider about using alias as resourceEntity)
     *
     * @param resource
     * @return
     */
    String correctResource(String resource);

    IKProtectionLink getLink(KResourceAccess rcAccess);
}
