package com.taoswork.tallycheck.testmaterial.jpa.domain.nature.impl;

import com.taoswork.tallycheck.datadomain.base.entity.MapField;
import com.taoswork.tallycheck.datadomain.base.entity.MapMode;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.impl.PhoneNumberByType;
import com.taoswork.tallycheck.testmaterial.jpa.domain.common.PhoneType;
import com.taoswork.tallycheck.testmaterial.jpa.domain.nature.ICitizen;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Map;

@Entity
@Table(name = "CITIZEN")
public class CitizenImpl implements ICitizen {
    @Id
    private long id;

    @Column(name = "F_NAME")
    private String firstName;
    @Column(name = "L_NAME")
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Calendar birth;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @ElementCollection
    @CollectionTable(name = "EMP_PHONE")
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUM")
    @MapField(mode = MapMode.Basic, entryDelegate = PhoneNumberByType.class)
    private Map<PhoneType, String> phoneNumbers;
// ...
}