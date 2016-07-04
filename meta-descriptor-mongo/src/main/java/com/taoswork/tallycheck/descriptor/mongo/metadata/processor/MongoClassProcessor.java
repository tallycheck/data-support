package com.taoswork.tallycheck.descriptor.mongo.metadata.processor;

import com.taoswork.tallycheck.descriptor.metadata.processor.GeneralClassProcessor;
import com.taoswork.tallycheck.descriptor.metadata.processor.IFieldProcessor;

public class MongoClassProcessor
        extends GeneralClassProcessor {

    @Override
    protected IFieldProcessor createTopFieldProcessor() {
        return new MongoFieldProcessor();
    }
}
