package com.taoswork.tallycheck.datasolution.config.beanlist;

import com.taoswork.tallycheck.datasolution.core.description.FriendlyMetaInfoService;
import com.taoswork.tallycheck.datasolution.security.impl.SecurityVerifierAgent;
import com.taoswork.tallycheck.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import org.springframework.context.MessageSource;

/**
 * Created by Gao Yuan on 2015/7/5.
 */
public interface IDataSolutionSupporterBeanList {

    RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer();

    MessageSource entityMessageSource();

    MessageSource errorMessageSource();

    FriendlyMetaInfoService friendlyMetaInfoService();

    SecurityVerifierAgent securityVerifierAgent();
}
