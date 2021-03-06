package com.taoswork.tallycheck.testmaterial.mongo.domain.zoo;

import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import com.taoswork.tallycheck.testmaterial.mongo.domain.common.Address;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity
public class Dormitory extends AbstractDocument {

    protected String name;

    @Embedded
    private Address address;

    @Reference
    private Zoo zoo;

    //The guy lives in this dormitory
    @Reference
    protected ZooKeeperImpl zooKeeper;
    public static final String ZOOKEEPER_COLUMN_NAME = "zooKeeper";

    @Override
    public String getInstanceName() {
        return name;
    }
}
