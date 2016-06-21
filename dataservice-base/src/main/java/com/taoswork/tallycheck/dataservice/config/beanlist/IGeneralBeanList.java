package com.taoswork.tallycheck.dataservice.config.beanlist;

import com.taoswork.tallycheck.general.solution.spring.BeanCreationMonitor;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IGeneralBeanList {
    BeanCreationMonitor beanCreationMonitor();

    String dataServiceName();

}
