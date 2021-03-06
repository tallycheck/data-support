package com.taoswork.tallycheck.descriptor.service.impl;

import com.taoswork.tallycheck.descriptor.description.builder.EntityInfoBuilder;
import com.taoswork.tallycheck.descriptor.description.builder.m2i.FM2IPool;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.general.solution.threading.annotations.ThreadSafe;
import com.taoswork.tallycheck.info.IEntityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Created by Gao Yuan on 2015/5/27.
 */
@ThreadSafe
public class BaseMetaInfoServiceImpl implements
        MetaInfoService {
    private static Logger LOGGER = LoggerFactory.getLogger(BaseMetaInfoServiceImpl.class);

    private final FM2IPool fm2IPool;

    public BaseMetaInfoServiceImpl(){
        fm2IPool = createFM2IPool();
    }

    protected FM2IPool createFM2IPool(){
        return new FM2IPool();
    }

    @Override
    public IEntityInfo generateEntityInfo(IClassMeta classMeta, EntityInfoType infoType) {
        EntityInfo entityInfo = generateEntityMainInfo(classMeta);
        return convert(entityInfo, infoType);
    }

    @Override
    public EntityInfo generateEntityMainInfo(IClassMeta classMeta) {
        EntityInfo entityInfo = new EntityInfoBuilder(fm2IPool).build(classMeta);
        return entityInfo;
    }

    @Override
    public IEntityInfo convert(EntityInfo entityInfo, EntityInfoType type) {
        Class<? extends IEntityInfo> cls = type.getEntityInfoClass();
        if (cls != null) {
            try {
                Constructor cons = cls.getConstructor(new Class[]{EntityInfo.class});
                IEntityInfo cinfo = (IEntityInfo) cons.newInstance(entityInfo);
                return cinfo;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return null;
    }
}
