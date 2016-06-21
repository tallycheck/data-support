package com.taoswork.tallycheck.testmaterial.general.domain.meta.valuecopier;

import com.taoswork.tallycheck.datadomain.base.entity.valuecopier.IEntityCopier;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class ACopier implements IEntityCopier {
    @Override
    public boolean allHandled() {
        return false;
    }

    @Override
    public Collection<String> handledFields() {
        return null;
    }

    @Override
    public void copy(Object src, Object target) {

    }
}
