package com.taoswork.tallycheck.dataservice.core.entityprotect.valuegate;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.valuegate.EntityGatePool;
import com.taoswork.tallycheck.datadomain.base.entity.valuegate.IEntityGate;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;

import java.util.Collection;

public class EntityValueGateManager
        extends EntityGatePool
        implements EntityValueGate {

    @Override
    public void store(IClassMeta classMeta, Persistable entity, Persistable oldEntity) throws ServiceException {
        Collection<String> gateNames = classMeta.getReadonlyValueGates();
        for (String gateName : gateNames) {
            IEntityGate gate = getValueGate(gateName);
            gate.store(entity, oldEntity);
        }
    }

    @Override
    public void fetch(IClassMeta classMeta, Persistable entity) {
        Collection<String> gateNames = classMeta.getReadonlyValueGates();
        for (String gateName : gateNames) {
            IEntityGate gate = getValueGate(gateName);
            gate.fetch(entity);
        }
    }

}
