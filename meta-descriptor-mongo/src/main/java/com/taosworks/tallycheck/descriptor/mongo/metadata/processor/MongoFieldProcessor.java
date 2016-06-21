package com.taosworks.tallycheck.descriptor.mongo.metadata.processor;

import com.taoswork.tallycheck.descriptor.metadata.processor.GeneralFieldProcessor;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.fields.basics.BasicFieldHandler;
import com.taosworks.tallycheck.descriptor.mongo.metadata.processor.field.ObjectIdFieldMetaHandler;
import com.taosworks.tallycheck.descriptor.mongo.metadata.processor.handler.MongoAnnotationFieldHandler;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class MongoFieldProcessor
        extends GeneralFieldProcessor {

    @Override
    protected void addVendorPersistSupport() {
        super.addVendorPersistSupport();
        metaHandlers.add(new MongoAnnotationFieldHandler());
    }

    @Override
    protected void addBasicFieldHandler(BasicFieldHandler basicFieldHandler) {
        super.addBasicFieldHandler(basicFieldHandler);
        basicFieldHandler.add(new ObjectIdFieldMetaHandler());
    }
}
