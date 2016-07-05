package com.taoswork.tallycheck.datasolution.jpa.config.beanlist;

import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.JpaEntityService;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.PersistenceService;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;

/**
 * Created by Gao Yuan on 2015/7/6.
 */
public interface IEntityBeanList {

    JpaEntityService dynamicEntityService();

    AnnotationAwareAspectJAutoProxyCreator annotationAwareAspectJAutoProxyCreator();

    PersistenceService dynamicEntityPersistenceService();
}
