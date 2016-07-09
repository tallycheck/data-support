package com.taoswork.tallycheck.descriptor.dataio.copier;

import com.taoswork.tallycheck.descriptor.metadata.IClassMetaAccess;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class CopierContext {
    public final IClassMetaAccess classMetaAccess;
    public final ExternalReference externalReference;

    public CopierContext(IClassMetaAccess classMetaAccess, ExternalReference externalReference) {
        this.classMetaAccess = classMetaAccess;
        this.externalReference = externalReference;
    }
}
