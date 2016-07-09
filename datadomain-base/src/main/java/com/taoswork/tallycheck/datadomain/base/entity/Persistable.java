package com.taoswork.tallycheck.datadomain.base.entity;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public interface Persistable extends Serializable {
    Object getInstanceId();

    String getInstanceName();
}
