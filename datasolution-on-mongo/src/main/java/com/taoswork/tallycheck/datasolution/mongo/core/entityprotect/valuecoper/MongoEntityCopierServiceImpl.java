package com.taoswork.tallycheck.datasolution.mongo.core.entityprotect.valuecoper;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.datadomain.base.entity.valuecopier.EntityCopierPool;
import com.taoswork.tallycheck.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.datasolution.service.EntityCopierService;
import com.taoswork.tallycheck.descriptor.dataio.copier.CopyException;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.VFieldCopierSolution;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class MongoEntityCopierServiceImpl implements EntityCopierService {
    private final EntityCopierPool entityCopierPool;
    private final IFieldCopierSolution fieldCopierSolution;

    public MongoEntityCopierServiceImpl() {
        entityCopierPool = new EntityCopierPool();
        fieldCopierSolution = new VFieldCopierSolution(entityCopierPool);
    }

    @Override
    public <T extends Persistable> T makeSafeCopy(CopierContext copierContext, T rec, int levelLimit) throws ServiceException {
        try {
            return fieldCopierSolution.makeSafeCopy(rec, copierContext, levelLimit);
        } catch (CopyException e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }

    @Override
    public <T extends Persistable> T makeSafeCopy(CopierContext copierContext, T rec, CopyLevel level) throws ServiceException {
        try {
            return fieldCopierSolution.makeSafeCopy(rec, copierContext, level);
        } catch (CopyException e) {
            throw ServiceException.treatAsServiceException(e);
        }
    }

}
