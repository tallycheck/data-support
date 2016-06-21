package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.map.LookupMapFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class VLookupMapFieldCopier extends BaseVMapFieldCopier<LookupMapFieldMeta> {
    public VLookupMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends LookupMapFieldMeta> targetMeta() {
        return LookupMapFieldMeta.class;
    }

}
