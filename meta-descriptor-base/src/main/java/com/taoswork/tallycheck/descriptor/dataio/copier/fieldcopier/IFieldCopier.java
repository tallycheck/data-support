package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier;

import com.taoswork.tallycheck.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public interface IFieldCopier {
    Class<? extends IFieldMeta> targetMeta();

    boolean copy(final IClassMeta topMeta, final IFieldMeta fieldMeta,
                 final Object source, final Object target,
                 final int currentLevel, final int levelLimit,
                 final CopierContext copierContext)
            throws IllegalAccessException, InstantiationException;
}
