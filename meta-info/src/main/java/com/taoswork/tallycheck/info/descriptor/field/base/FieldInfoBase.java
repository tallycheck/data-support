package com.taoswork.tallycheck.info.descriptor.field.base;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import com.taoswork.tallycheck.info.descriptor.base.impl.NamedOrderedImpl;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
abstract class FieldInfoBase
        extends NamedOrderedImpl
        implements IFieldInfoRW {

    public int visibility = Visibility.DEFAULT;
    public FieldType fieldType = FieldType.UNKNOWN;

    public boolean editable = true;
    public boolean required = false;
    public boolean _ignored = false;

    public FieldInfoBase() {
    }

    public FieldInfoBase(String name, String friendlyName, boolean editable) {
        super(name, friendlyName);
        this.editable = editable;
        visibility = Visibility.DEFAULT;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    @Override
    public int getVisibility() {
        return visibility;
    }

    @Override
    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    @Override
    public boolean isFormVisible() {
        return Visibility.formVisible(visibility);
    }

    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public void setRequired(boolean required) {
        this.required = required;
    }

    @Override
    public boolean ignored() {
        return _ignored;
    }

    @Override
    public void setIgnored(boolean ignored) {
        this._ignored = ignored;
    }

    @Override
    public FieldType getFieldType() {
        if (fieldType == null) {
            return FieldType.UNKNOWN;
        }
        return fieldType;
    }

    @Override
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return this.name + ", " + this.fieldType;
    }
}
