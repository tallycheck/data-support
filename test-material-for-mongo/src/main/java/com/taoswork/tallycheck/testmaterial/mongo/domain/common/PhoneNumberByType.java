package com.taoswork.tallycheck.testmaterial.mongo.domain.common;

import com.taoswork.tallycheck.datadomain.base.presentation.typedcollection.mapentry.IMapEntryDelegate;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public class PhoneNumberByType implements IMapEntryDelegate {
    private PhoneType type;
    private String number;

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
