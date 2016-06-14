package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallybook.datadomain.base.entity.Persistable;
import com.taoswork.tallybook.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallybook.descriptor.dataio.copier.CopyException;
import com.taoswork.tallybook.descriptor.metadata.IClassMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public interface IFieldCopierSolution {

    <T> T walkFieldsAndCopy(final IClassMeta topMeta, IClassMeta classMeta,
                            T source, final int currentLevel, final int levelLimit, CopierContext copierContext) throws IllegalAccessException, InstantiationException;

    <T extends Persistable> T makeSafeCopy(T rec, CopierContext copierContext, int levelLimit) throws CopyException;

    <T extends Persistable> T makeSafeCopy(T rec, CopierContext copierContext, CopyLevel level) throws CopyException;
}
