package com.taoswork.tallycheck.descriptor.description.builder.m2i;

import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.IFieldMeta;
import com.taoswork.tallycheck.info.descriptor.field.IFieldInfo;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/22.
 */
public interface IFM2I {
    Class<? extends IFieldMeta> targetMeta();

    IFieldInfo createInfo(IClassMeta topMeta, String prefix, IFieldMeta fieldMeta, Collection<Class> collectionTypeReferenced);
}
