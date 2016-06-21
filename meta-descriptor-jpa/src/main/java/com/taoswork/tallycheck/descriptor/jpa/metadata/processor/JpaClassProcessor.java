package com.taoswork.tallycheck.descriptor.jpa.metadata.processor;

import com.taoswork.tallycheck.descriptor.metadata.processor.GeneralClassProcessor;
import com.taoswork.tallycheck.descriptor.metadata.processor.IFieldProcessor;

public class JpaClassProcessor
        extends GeneralClassProcessor {

    @Override
    protected IFieldProcessor createTopFieldProcessor() {
        return new JpaFieldProcessor();
    }
}
