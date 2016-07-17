package com.taoswork.tallycheck.info.descriptor.field.typed;

import com.taoswork.tallycheck.info.descriptor.field.base.BasicFieldInfoBase;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class StringFieldInfo extends BasicFieldInfoBase {
    private int length;

    public StringFieldInfo() {
    }

    public StringFieldInfo(String name, String friendlyName, boolean editable, int length) {
        super(name, friendlyName, editable);
        this.length = length;
    }

    public int getLength() {
        return length;
    }

}