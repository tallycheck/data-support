package com.taoswork.tallycheck.datasolution.security;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.operator.Operator;

/**
 * Created by gaoyuan on 7/18/16.
 */
public interface AccessInterrupter {
    String getResource();

    void probeCreate(Operator operator, Persistable entity) throws SecurityException;

    void probeRead(Operator operator, Persistable entity) throws SecurityException;

    void probeUpdate(Operator operator, Persistable entity, Persistable oldEntity) throws SecurityException;

    void probeDelete(Operator operator, Persistable entity) throws SecurityException;

}