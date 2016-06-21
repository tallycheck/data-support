package com.taoswork.tallycheck.descriptor.jpa.metadata.processor;

import com.taoswork.tallycheck.descriptor.metadata.processor.GeneralFieldProcessor;
import com.taoswork.tallycheck.descriptor.jpa.metadata.processor.handler.jpafields.JpaAnnotationFieldHandler;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public class JpaFieldProcessor
        extends GeneralFieldProcessor {

    @Override
    protected void addVendorPersistSupport() {
        super.addVendorPersistSupport();
        metaHandlers.add(new JpaAnnotationFieldHandler());
    }
}
