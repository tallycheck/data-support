package com.taoswork.tallycheck.datasolution.jpa.config.beanlist;

import com.taoswork.tallycheck.datasolution.jpa.JpaDatasourceDefinition;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.OpenEntityManagerAop;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.PersistenceManager;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.PersistenceManagerFactory;
import com.taoswork.tallycheck.datasolution.jpa.core.persistence.PersistenceManagerInvoker;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IPersistenceBeanList {
    JpaDatasourceDefinition dataServiceDefinitionBean();

    DataSource serviceDataSource();

    AbstractEntityManagerFactoryBean entityManagerFactory();

    JpaTransactionManager jpaTransactionManager();

    PersistenceManagerFactory persistenceManagerFactory();

    PersistenceManager persistenceManager();

    PersistenceManagerInvoker persistenceManagerInvoker();

    OpenEntityManagerAop openEntityManagerAop();
}
