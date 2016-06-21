package com.taoswork.tallycheck.testmaterial.mongo.domain.zoo;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;

/**
 * Created by Gao Yuan on 2016/2/17.
 */
public interface ZooKeeper extends PersistableDocument {
    String getName();

    ZooKeeperImpl setName(String name);

    String getEmail();

    ZooKeeperImpl setEmail(String emailAddress);

    String getMobile();

    ZooKeeperImpl setMobile(String mobile);

    String getUuid();

    ZooKeeperImpl setUuid(String uuid);
}
