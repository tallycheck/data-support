package com.taoswork.tallycheck.datadomain.base.entity;

/**
 * Created by Gao Yuan on 2015/11/1.
 */

import com.taoswork.tallycheck.datadomain.base.entity.valuegate.IFieldGate;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PersistField {

    public static final int DEFAULT_LENGTH = 255;

    /**
     * if the field has its own value gate
     *
     * @return
     */
    Class<? extends IFieldGate> fieldValueGateOverride() default IFieldGate.class;

    /**
     * whether to invoke default field value gate
     *
     * @return
     */
    boolean skipDefaultFieldValueGate() default true;

    /**
     * if the field is a not null field.
     *
     * @return
     */
    boolean required() default false;

    /**
     * if this field is allowed to edit from the client
     *
     * @return
     */
    boolean editable() default true;

    /**
     * works for string type only
     * @return
     */
    int length() default DEFAULT_LENGTH;

    /**
     * Optional - only required if you want to explicitly specify the field type. This
     * value is normally inferred by the system based on the field type in the entity class.
     * <p>
     * Explicity specify the type the GUI should consider this field
     * Specifying UNKNOWN will cause the system to make its best guess
     *
     * @return the field type
     */
    FieldType fieldType() default FieldType.UNKNOWN;

}
