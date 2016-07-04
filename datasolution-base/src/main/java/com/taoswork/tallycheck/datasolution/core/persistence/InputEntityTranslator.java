package com.taoswork.tallycheck.datasolution.core.persistence;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.TranslateException;
import com.taoswork.tallycheck.descriptor.metadata.IClassMetaAccess;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class InputEntityTranslator extends EntityTranslator {

    public Persistable convert(final IClassMetaAccess cmAccess, Entity source, String id) throws ServiceException {
        try {
            return super.translate(cmAccess, source, id);
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }
    }
}
