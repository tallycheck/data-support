package com.taoswork.tallycheck.info.descriptor.base;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface Named extends Serializable {

    String getName();

    String getFriendlyName();

    Named setName(String name);

    Named setFriendlyName(String friendlyName);
}
