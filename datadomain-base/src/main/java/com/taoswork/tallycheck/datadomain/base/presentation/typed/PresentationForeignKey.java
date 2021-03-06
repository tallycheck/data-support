package com.taoswork.tallycheck.datadomain.base.presentation.typed;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface PresentationForeignKey {
    Class targetEntity() default void.class;

    String idField() default "id";

    String displayField() default "name";
}
