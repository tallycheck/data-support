package com.taoswork.tallycheck.descriptor.service.impl;

import com.taoswork.tallycheck.descriptor.metadata.processor.GeneralClassProcessor;
import com.taoswork.tallycheck.descriptor.metadata.processor.GeneralFieldProcessor;
import com.taoswork.tallycheck.descriptor.metadata.processor.IFieldProcessor;

/**
 * Created by Gao Yuan on 2016/3/30.
 */
public class GeneralMetaServiceImpl extends BaseMetaServiceImpl{
    public GeneralMetaServiceImpl() {
        super(new GeneralClassProcessor() {
            @Override
            protected IFieldProcessor createTopFieldProcessor() {
                return new GeneralFieldProcessor();
            }
        });
    }
}
