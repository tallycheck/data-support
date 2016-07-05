package com.taoswork.tallycheck.datasolution.service;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;

/**
 * Created by Gao Yuan on 2015/10/5.
 */
public interface EntityCopierService {
    public final static String COMPONENT_NAME = "EntityCopierService";

    <T extends Persistable> T makeSafeCopy(CopierContext copierContext, T rec, int levelLimit) throws ServiceException;

    <T extends Persistable> T makeSafeCopy(CopierContext copierContext, T rec, CopyLevel level) throws ServiceException;
}
