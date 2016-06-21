package com.taoswork.tallycheck.dataservice.config.beanlist;

import com.taoswork.tallycheck.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallycheck.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallycheck.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IDataServiceSupporterBeanList {

    RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer();

    MessageSource entityMessageSource();

    MessageSource errorMessageSource();

    FriendlyMetaInfoService friendlyMetaInfoService();

    SecurityVerifierAgent securityVerifierAgent();
}
