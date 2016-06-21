package com.taoswork.tallycheck.descriptor.jpa.metadata.processor.handler.jpafields;

import com.taoswork.tallycheck.datadomain.base.presentation.typed.PresentationForeignKey;
import com.taoswork.tallycheck.descriptor.metadata.exception.MetadataException;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.FieldMetaMediate;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.ForeignEntityFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.processor.ProcessResult;
import com.taoswork.tallycheck.descriptor.metadata.processor.handler.basic.IFieldHandler;

import javax.persistence.OneToOne;
import java.lang.reflect.Field;

/**
 * Created by Gao Yuan on 2016/2/19.
 */
public class _OneToOneAnnotationHandler implements IFieldHandler {
    @Override
    public ProcessResult process(Field field, FieldMetaMediate metaMediate) {
        OneToOne toOne = field.getDeclaredAnnotation(OneToOne.class);
        if(toOne != null){
            Class toOneTargetEntity = toOne.targetEntity();
            if(toOneTargetEntity == void.class){
                toOneTargetEntity = field.getType();
            }
            PresentationForeignKey pfk = field.getDeclaredAnnotation(PresentationForeignKey.class);
            if(pfk != null){
                Class pfkTargetEntity = pfk.targetEntity();
                if(pfkTargetEntity == void.class){
                    pfkTargetEntity = field.getType();
                }
                if(!pfkTargetEntity.equals(toOneTargetEntity)){
                    MetadataException.throwFieldConfigurationException(field);
                }
            }
            ForeignEntityFieldMeta.Facet facet = new ForeignEntityFieldMeta.Facet(toOneTargetEntity);
            metaMediate.pushFacet(facet);
            return ProcessResult.PASSING_THROUGH;
        }
        return ProcessResult.INAPPLICABLE;
    }
}
