package com.taoswork.tallycheck.dataservice.core.entityprotect.field.handler;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;

public interface ITypedFieldHandler {

    FieldType supportedFieldType();

    Class supportedFieldClass();
}
