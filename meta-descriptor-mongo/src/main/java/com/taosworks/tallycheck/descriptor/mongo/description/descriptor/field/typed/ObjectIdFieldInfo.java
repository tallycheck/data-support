package com.taosworks.tallycheck.descriptor.mongo.description.descriptor.field.typed;

import com.taoswork.tallycheck.descriptor.description.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2016/3/15.
 */
public class ObjectIdFieldInfo extends BasicFieldInfoBase {

    public ObjectIdFieldInfo(String name, String friendlyName, boolean editable) {
        super(name, friendlyName, editable);
    }
}