package com.taoswork.tallycheck.dataservice.service.impl;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.core.entityprotect.field.valuegate.gates.EmailGate;
import com.taoswork.tallycheck.dataservice.core.entityprotect.field.valuegate.gates.HtmlGate;
import com.taoswork.tallycheck.dataservice.core.entityprotect.valuegate.EntityValueGate;
import com.taoswork.tallycheck.dataservice.core.entityprotect.valuegate.EntityValueGateManager;
import com.taoswork.tallycheck.dataservice.core.entityprotect.valuegate.EntityValueGateOnFields;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.service.EntityMetaAccess;
import com.taoswork.tallycheck.dataservice.service.EntityValueGateService;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;

import javax.annotation.Resource;

public class EntityValueGateServiceImpl implements EntityValueGateService {
    @Resource(name = EntityMetaAccess.COMPONENT_NAME)
    protected EntityMetaAccess entityMetaAccess;

    private final EntityValueGate entityValueGateOnFields;
    private final EntityValueGate entityValueGateManager;

    public EntityValueGateServiceImpl() {
        EntityValueGateOnFields valueGateOnFields = new EntityValueGateOnFields();
        valueGateOnFields
                .addHandler(new EmailGate())
                .addHandler(new HtmlGate());
        entityValueGateOnFields = valueGateOnFields;
        entityValueGateManager = new EntityValueGateManager();
    }

    @Override
    public <T extends Persistable> void store(T entity, T oldEntity) throws ServiceException {
        Class entityType = entity.getClass();
        IClassMeta classMeta = entityMetaAccess.getClassMeta(entityType, false);

        entityValueGateOnFields.store(classMeta, entity, oldEntity);
        entityValueGateManager.store(classMeta, entity, oldEntity);
    }

    @Override
    public <T extends Persistable> void fetch(T entity) {
        Class entityType = entity.getClass();
        IClassMeta classMeta = entityMetaAccess.getClassMeta(entityType, false);

        entityValueGateManager.fetch(classMeta, entity);
    }
}
