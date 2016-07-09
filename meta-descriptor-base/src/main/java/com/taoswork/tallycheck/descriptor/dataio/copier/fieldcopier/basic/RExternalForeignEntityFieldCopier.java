package com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.basic;

import com.taoswork.tallycheck.descriptor.dataio.copier.CopierContext;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.BaseFieldCopier;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.IFieldCopierSolution;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.ExternalForeignEntityFieldMeta;
import com.taoswork.tallycheck.general.solution.reference.ExternalReference;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/23.
 */
public class RExternalForeignEntityFieldCopier extends BaseFieldCopier<ExternalForeignEntityFieldMeta> {
    public RExternalForeignEntityFieldCopier(IFieldCopierSolution solution) {
        super(solution);
    }

    @Override
    public Class<? extends ExternalForeignEntityFieldMeta> targetMeta() {
        return ExternalForeignEntityFieldMeta.class;
    }

    @Override
    protected boolean doCopy(IClassMeta topMeta, ExternalForeignEntityFieldMeta fieldMeta,
                             Object source, Object target, int currentLevel, int levelLimit,
                             CopierContext copierContext) throws IllegalAccessException, InstantiationException {
        ExternalForeignEntityFieldMeta efeFm = fieldMeta;
        Field foreignKeyField = fieldMeta.getField();
        Field foreignValField = efeFm.getEntityField();
        Object keyVal = foreignKeyField.get(source);
        foreignKeyField.set(target, keyVal);
        if (null == keyVal) {
            foreignValField.set(target, null);
        } else {
            final ExternalReference externalReference = (copierContext != null) ? copierContext.externalReference : null;
            if(externalReference != null) {
                Class entityType = efeFm.getTargetType();
                //backlog data: [type: entityType, key: keyVal]
                //slot: [target: target, position: foreignValField]
                externalReference.publishReference(target, foreignValField, entityType, keyVal.toString());
            }
            foreignValField.set(target, null);
//                    throw new IllegalAccessException("Not Implemented");
        }
        return true;
    }
}
