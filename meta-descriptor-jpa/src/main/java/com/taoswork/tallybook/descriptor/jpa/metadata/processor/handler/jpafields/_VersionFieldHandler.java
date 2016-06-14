package com.taoswork.tallybook.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallybook.datadomain.base.presentation.Visibility;
import com.taoswork.tallybook.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallybook.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallybook.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.Version;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2015/10/31.
 */
public class _VersionFieldHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field a, FieldMetaMediate metaMediate) {
        if (a.isAnnotationPresent(Version.class)) {
            metaMediate.getBasicFieldMetaObject().setVisibility(Visibility.HIDDEN_ALL);
            return ProcessResult.HANDLED;
        }
        return ProcessResult.PASSING_THROUGH;
    }
}
