package com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic;

import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;

public interface IMetaHandler<NativeType, MetaType> {

    ProcessResult process(NativeType a, MetaType aMeta);
}
