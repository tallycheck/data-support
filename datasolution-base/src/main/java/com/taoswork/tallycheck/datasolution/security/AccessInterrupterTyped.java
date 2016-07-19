package com.taoswork.tallycheck.datasolution.security;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.operator.Operator;

/**
 * Created by gaoyuan on 7/18/16.
 */
public class AccessInterrupterTyped<T> implements AccessInterrupter {
    private final String resource;

    public AccessInterrupterTyped(Class<? extends T> resourceType) {
        this.resource = resourceType.getName();
    }

    @Override
    public final String getResource() {
        return resource;
    }

    @Override
    public final void probeCreate(Operator operator, Persistable entity) throws SecurityException {
        probeCreateTyped(operator, (T) entity);
    }

    @Override
    public final void probeRead(Operator operator, Persistable entity) throws SecurityException {
        probeReadTyped(operator, (T) entity);
    }

    @Override
    public final void probeUpdate(Operator operator, Persistable entity, Persistable oldEntity) throws SecurityException {
        probeUpdateTyped(operator, (T) entity, (T) oldEntity);
    }

    @Override
    public final void probeDelete(Operator operator, Persistable entity) throws SecurityException {
        probeDeleteTyped(operator, (T) entity);
    }


    public void probeCreateTyped(Operator operator, T entity) throws SecurityException {

    }

    public void probeReadTyped(Operator operator, T entity) throws SecurityException {

    }

    public void probeUpdateTyped(Operator operator, T entity, T oldEntity) throws SecurityException {

    }

    public void probeDeleteTyped(Operator operator, T entity) throws SecurityException {

    }

}
