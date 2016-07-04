package com.taoswork.tallycheck.datasolution.core.entityprotect.field.valuegate;

import com.taoswork.tallycheck.datasolution.core.entityprotect.field.handler.BaseTypedFieldHandler;

public abstract class BaseTypedFieldGate<T>
        extends BaseTypedFieldHandler<T>
        implements TypedFieldGate {

    @Override
    public Object store(Object val, Object oldVal) {
        return this.doStore((T) val, (T) oldVal);
    }

    protected abstract T doStore(T val, T oldVal);
}
