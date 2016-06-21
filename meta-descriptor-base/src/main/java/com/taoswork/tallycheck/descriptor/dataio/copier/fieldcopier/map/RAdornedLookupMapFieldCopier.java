package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.map;

import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.map.AdornedLookupMapFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RAdornedLookupMapFieldCopier extends BaseRMapFieldCopier<AdornedLookupMapFieldMeta> {
    public RAdornedLookupMapFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends AdornedLookupMapFieldMeta> targetMeta() {
        return AdornedLookupMapFieldMeta.class;
    }
}
