package com.taoswork.tallycheck.descriptor.metadata.fieldmetadata;

import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;

import java.util.Collection;

public abstract class BaseNonCollectionFieldMeta extends BaseFieldMeta {

    public BaseNonCollectionFieldMeta(BasicFieldMetaObject bfmo) {
        super(bfmo);
    }

    @Override
    public boolean isCollectionField() {
        return false;
    }

    @Override
    public boolean isId() {
        return basicFieldMetaObject.isId();
    }

    @Override
    public boolean isNameField() {
        return basicFieldMetaObject.isNameField();
    }

    @Override
    public void setNameField(boolean b) {
        basicFieldMetaObject.setNameField(b);
    }

    @Override
    public boolean showOnGrid() {
        return Visibility.gridVisible(basicFieldMetaObject.getVisibility());
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        return 0;
    }
}
