package com.taoswork.tallycheck.testmaterial.mongo.domain.nature;

import com.taoswork.tallycheck.datadomain.base.entity.MapField;
import com.taoswork.tallycheck.datadomain.base.entity.MapMode;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallycheck.testmaterial.mongo.domain.common.PhoneNumberByType;
import com.taoswork.tallycheck.testmaterial.mongo.domain.common.PhoneType;
import org.mongodb.morphia.annotations.Entity;

import java.util.Calendar;
import java.util.Map;

@Entity
public class Citizen extends AbstractDocument {
    private String firstName;
    private String lastName;

    private Calendar birth;

    private String idCardNo;

    @MapField(mode = MapMode.Basic, entryDelegate = PhoneNumberByType.class)
    private Map<PhoneType, String> phoneNumbers;

    @Override
    public String getInstanceName() {
        return lastName + ", " + firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getBirth() {
        return birth;
    }

    public void setBirth(Calendar birth) {
        this.birth = birth;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Map<PhoneType, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<PhoneType, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    // ...
}