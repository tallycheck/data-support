package com.taoswork.tallycheck.descriptor.metadata.processor;

import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;

/**
 * Created by Gao Yuan on 2015/2/22.
 */
public interface IFieldProcessor extends IFieldHandler {
    void setParentClassProcessor(IClassProcessor classProcessor);
    void init();
}
