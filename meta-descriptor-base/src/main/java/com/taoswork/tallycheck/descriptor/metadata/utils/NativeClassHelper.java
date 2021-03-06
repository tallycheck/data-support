package com.taoswork.tallycheck.descriptor.metadata.utils;

import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;

import java.lang.reflect.Modifier;

/**
 * Created by Gao Yuan on 2015/5/22.
 */
public class NativeClassHelper {

    public static boolean isInstantiable(Class<?> clazz) {
        //We filter out abstract classes because they can't be instantiated.
        if (Modifier.isAbstract(clazz.getModifiers())) {
            return false;
        }

        //We filter out classes that are marked to exclude from polymorphism
        PersistEntity persistEntity = clazz.getAnnotation(PersistEntity.class);
        if (persistEntity == null) {
            return true;
        } else {
            return persistEntity.instantiable();
        }
    }


}
