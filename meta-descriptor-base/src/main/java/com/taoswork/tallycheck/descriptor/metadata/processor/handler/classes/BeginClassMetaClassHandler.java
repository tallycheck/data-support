package com.taoswork.tallycheck.descriptor.metadata.processor.handler.classes;

import com.taoswork.tallycheck.descriptor.metadata.classmetadata.MutableClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IClassHandler;
import com.taoswork.tallycheck.info.FriendlyNameHelper;

public class BeginClassMetaClassHandler implements IClassHandler {
    @Override
    public ProcessResult process(Class clz, MutableClassMeta mutableClassMetadata) {
        mutableClassMetadata.setName(clz.getName());
        mutableClassMetadata.setFriendlyName(FriendlyNameHelper.makeFriendlyName4Class(clz));

        return ProcessResult.PASSING_THROUGH;
    }
}
