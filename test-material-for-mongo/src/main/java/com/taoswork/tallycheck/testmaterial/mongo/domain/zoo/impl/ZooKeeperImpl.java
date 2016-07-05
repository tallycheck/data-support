package com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.impl;

import com.taoswork.tallycheck.datadomain.base.entity.MapField;
import com.taoswork.tallycheck.datadomain.base.entity.MapMode;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallycheck.testmaterial.mongo.domain.common.PhoneNumberByType;
import com.taoswork.tallycheck.testmaterial.mongo.domain.common.PhoneType;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.Dormitory;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.Zoo;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.ZooKeeper;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.ZooKeeperGate;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.Map;

/**
 * Created by Gao Yuan on 2015/4/15.
 */
@Entity("zookeeper")
@PresentationClass(
)
@PersistEntity(valueGates = {ZooKeeperGate.class})
public class ZooKeeperImpl extends AbstractDocument implements ZooKeeper {

    protected String name;

    protected String email;

    @PersistField(fieldType = FieldType.PHONE)
    @PresentationField
    protected String mobile;

    @MapField(mode = MapMode.Basic, entryDelegate = PhoneNumberByType.class)
    protected Map<PhoneType, String> phoneNumbers;

    @Reference
    protected Dormitory dormitory;

    @PresentationField(visibility = Visibility.GRID_HIDE)
    protected String uuid;

    @Reference
    protected Zoo zoo;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ZooKeeperImpl setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public ZooKeeperImpl setEmail(String emailAddress) {
        this.email = emailAddress;
        return this;
    }

    @Override
    public String getMobile() {
        return mobile;
    }

    @Override
    public ZooKeeperImpl setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    @Override
    public String getUuid() {
        return uuid;
    }

    @Override
    public ZooKeeperImpl setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @Override
    public String toString() {
        return "ZooKeeperImpl[#" + this.getId() +
                " '" + name + '\'' +
                " {" + uuid + '}' +
                ']';
    }
}
