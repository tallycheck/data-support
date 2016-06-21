package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.map.BasicMapFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class VBasicMapFieldCopier extends BaseVMapFieldCopier<BasicMapFieldMeta> {
    public VBasicMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends BasicMapFieldMeta> targetMeta() {
        return BasicMapFieldMeta.class;
    }

    @Override
    protected MapEntry makeMapEntryCopy(IClassMeta topMeta, BasicMapFieldMeta fieldMeta, MapEntry element, int currentLevel, int levelLimit) {
        return super.makeMapEntryCopy(topMeta, fieldMeta, element, currentLevel, levelLimit);
    }

}
