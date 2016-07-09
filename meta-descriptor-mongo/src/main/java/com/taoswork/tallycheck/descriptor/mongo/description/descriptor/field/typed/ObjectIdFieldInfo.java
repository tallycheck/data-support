package com.taoswork.tallycheck.descriptor.mongo.description.descriptor.field.typed;

import com.taoswork.tallycheck.info.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
public class ObjectIdFieldInfo extends BasicFieldInfoBase {

    public ObjectIdFieldInfo(String name, String friendlyName, boolean editable) {
        super(name, friendlyName, editable);
    }
}