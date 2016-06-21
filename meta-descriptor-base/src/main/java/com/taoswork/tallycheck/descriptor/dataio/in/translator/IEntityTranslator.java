package com.taoswork.tallycheck.descriptor.dataio.in.translator;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;
import com.taoswork.tallycheck.descriptor.metadata.IClassMetaAccess;

/**
 * Created by Gao Yuan on 2016/3/21.
 */
public interface IEntityTranslator {
    Persistable translate(IClassMetaAccess cmAccess, Entity source, String id) throws TranslateException;
}
