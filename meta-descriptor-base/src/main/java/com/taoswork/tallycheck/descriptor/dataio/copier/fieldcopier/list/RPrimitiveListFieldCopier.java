package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list.PrimitiveListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RPrimitiveListFieldCopier extends BaseRListFieldCopier<PrimitiveListFieldMeta> {
    public RPrimitiveListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends PrimitiveListFieldMeta> targetMeta() {
        return PrimitiveListFieldMeta.class;
    }

    @Override
    protected Object makeListEntryCopy(IClassMeta topMeta, PrimitiveListFieldMeta fieldMeta, Object element, int currentLevel, int levelLimit) {
        return element;
    }
}
