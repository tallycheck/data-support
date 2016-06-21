package com.taosworks.tallycheck.descriptor.mongo.metadata.processor.handler;

import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;
import org.mongodb.morphia.annotations.Version;

import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/10/31.
 */
public class _VersionFieldHandler implements IFieldHandler {
//    @Override
//    public ProcessResult process(Field a, FieldMetaHolder fmHolder) {
//        if (a.isAnnotationPresent(Version.class)) {
//            fmHolder.getBasicFieldMetaObject().setVisibility(Visibility.HIDDEN_ALL);
//            return ProcessResult.HANDLED;
//        }
//        return ProcessResult.PASSING_THROUGH;
//    }
	    @Override
    public ProcessResult process(Field a, FieldMetaMediate metaMediate) {
        if (a.isAnnotationPresent(Version.class)) {
            metaMediate.getBasicFieldMetaObject().setVisibility(Visibility.HIDDEN_ALL);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.PASSING_THROUGH;
    }
}
