package com.taosworks.tallycheck.descriptor.mongo.service;

import com.taoswork.tallycheck.descriptor.service.impl.BaseMetaServiceImpl;
import com.taosworks.tallycheck.descriptor.mongo.metadata.processor.MongoClassProcessor;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MongoMetaServiceImpl extends BaseMetaServiceImpl {
    public MongoMetaServiceImpl() {
        super(new MongoClassProcessor());
    }
}