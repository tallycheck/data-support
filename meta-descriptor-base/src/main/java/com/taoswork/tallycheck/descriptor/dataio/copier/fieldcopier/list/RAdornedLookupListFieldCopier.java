package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.list;

import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list.AdornedLookupListFieldMeta;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RAdornedLookupListFieldCopier extends BaseRListFieldCopier<AdornedLookupListFieldMeta> {
    public RAdornedLookupListFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends AdornedLookupListFieldMeta> targetMeta() {
        return AdornedLookupListFieldMeta.class;
    }

}
