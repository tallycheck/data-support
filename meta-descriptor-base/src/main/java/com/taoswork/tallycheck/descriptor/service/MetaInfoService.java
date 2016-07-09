package com.taoswork.tallycheck.descriptor.service;

import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.info.IEntityInfo;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
public interface MetaInfoService {
    public static final String SERVICE_NAME = "MetaInfoService";

    EntityInfo generateEntityMainInfo(IClassMeta classMeta);

    IEntityInfo generateEntityInfo(IClassMeta classMeta, EntityInfoType infoType);

    IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type);

}
