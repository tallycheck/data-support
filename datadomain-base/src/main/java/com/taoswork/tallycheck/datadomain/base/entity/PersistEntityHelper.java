package com.taoswork.tallycheck.datadomain.base.entity;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Gao Yuan on 2015/12/19.
 */
public class PersistEntityHelper {
    public static final String getEntityName(Class<?> entityInterface) {
        String typeName = null;
        PersistEntity persistEntity = entityInterface.getDeclaredAnnotation(PersistEntity.class);
        if (persistEntity != null) {
            String nameOverride = persistEntity.value();
            if (StringUtils.isNotEmpty(nameOverride))
                typeName = nameOverride;
        }
        if(typeName == null){
            typeName = entityInterface.getSimpleName().toLowerCase();
        }
        return typeName;
    }
}
