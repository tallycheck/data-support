package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list.EntityListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class REntityListFieldCopier extends BaseRListFieldCopier<EntityListFieldMeta> {
    public REntityListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends EntityListFieldMeta> targetMeta() {
        return EntityListFieldMeta.class;
    }
}
