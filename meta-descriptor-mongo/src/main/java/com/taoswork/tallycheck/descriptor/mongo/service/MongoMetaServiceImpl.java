package com.taoswork.tallycheck.descriptor.mongo.service;

import com.taoswork.tallycheck.descriptor.mongo.metadata.processor.MongoClassProcessor;
import com.taoswork.tallycheck.descriptor.service.impl.BaseMetaServiceImpl;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MongoMetaServiceImpl extends BaseMetaServiceImpl {
    public MongoMetaServiceImpl() {
        super(new MongoClassProcessor());
    }
}