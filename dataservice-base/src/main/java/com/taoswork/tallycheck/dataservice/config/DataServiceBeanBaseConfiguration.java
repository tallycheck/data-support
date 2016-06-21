package com.taoswork.tallycheck.dataservice.config;

import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.dataservice.IDataServiceDefinition;
import com.taoswork.tallycheck.dataservice.IDataServiceDelegate;
import com.taoswork.tallycheck.dataservice.config.beanlist.IDataServiceSupporterBeanList;
import com.taoswork.tallycheck.dataservice.config.beanlist.IEntityProtectionBeanList;
import com.taoswork.tallycheck.dataservice.config.beanlist.IGeneralBeanList;
import com.taoswork.tallycheck.dataservice.config.helper.DataServiceBeanCreationHelper;
import com.taoswork.tallycheck.dataservice.core.description.FriendlyMetaInfoService;
import com.taoswork.tallycheck.dataservice.core.description.impl.FriendlyMetaInfoServiceImpl;
import com.taoswork.tallycheck.dataservice.security.impl.SecurityVerifierAgent;
import com.taoswork.tallycheck.dataservice.service.EntityValidationService;
import com.taoswork.tallycheck.dataservice.service.EntityValueGateService;
import com.taoswork.tallycheck.dataservice.service.impl.EntityValidationServiceImpl;
import com.taoswork.tallycheck.dataservice.service.impl.EntityValueGateServiceImpl;
import com.taoswork.tallycheck.general.solution.property.RuntimeEnvironmentPropertyPlaceholderConfigurer;
import com.taoswork.tallycheck.general.solution.spring.BeanCreationMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Gao Yuan on 2016/2/13.
 */
@Configuration
public class DataServiceBeanBaseConfiguration
    implements
        ApplicationContextAware,
        IGeneralBeanList,
        IDataServiceSupporterBeanList,
        IEntityProtectionBeanList
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceBeanBaseConfiguration.class);

    private IDataServiceDefinition dsDefinition;
    protected DataServiceBeanCreationHelper helper;

    // **************************************************** //
    //  Constructor & initialize                            //
    // **************************************************** //

    public DataServiceBeanBaseConfiguration() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(applicationContext instanceof IDataServiceDelegate){
            IDataServiceDelegate delegate = ((IDataServiceDelegate)applicationContext);
            IDataServiceDefinition dataServiceDefinition = delegate.getDataServiceDefinition();
            this.dsDefinition = dataServiceDefinition;
            helper = new DataServiceBeanCreationHelper(dsDefinition);
        }else {
            LOGGER.error("'{}' expected to be loaded by IDataServiceDefinitionHolder", this.getClass().getName());
            throw new IllegalArgumentException(this.getClass().getName() + " expected to be loaded by IDataServiceDefinitionHolder");
        }

    }

    /************************************************************
     * IGeneralBeanList                                         *
     * ({@link IGeneralBeanList})
     ************************************************************/

    @Override
    @Bean
    public BeanCreationMonitor beanCreationMonitor() {
        return new BeanCreationMonitor(dsDefinition.getDataServiceName());
    }

    @Override
    @Bean(name = IDataService.DATASERVICE_NAME_S_BEAN_NAME)
    public String dataServiceName() {
        return dsDefinition.getDataServiceName();
    }

    /* IGeneralBeanList                                        **/

    /************************************************************
     * IDataServiceSupporterBeanList                            *
     * ({@link IDataServiceSupporterBeanList})
     ************************************************************/

    @Override
    @Bean(name = IDataService.PROPERTY_CONFIGURER)
    public RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer() {
        return helper.createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    @Override
    @Bean(name = FriendlyMetaInfoService.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource entityMessageSource(){
        return helper.createEntityMessageSource();
    }

    @Override
    @Bean(name = IDataService.ERROR_MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource errorMessageSource() {
        return helper.createErrorMessageSource();
    }

    @Override
    @Bean(name = FriendlyMetaInfoService.SERVICE_NAME)
    public FriendlyMetaInfoService friendlyMetaInfoService(){
        return new FriendlyMetaInfoServiceImpl();
    }

    @Override
    @Bean(name = SecurityVerifierAgent.COMPONENT_NAME)
    public SecurityVerifierAgent securityVerifierAgent() {
        return new SecurityVerifierAgent();
    }
    /* IDataServiceSupporterBeanList                            */



    /************************************************************
     * IEntityProtectionBeanList                            *
     * ({@link IEntityProtectionBeanList})
     ************************************************************/

    @Override
    @Bean(name = EntityValidationService.COMPONENT_NAME)
    public EntityValidationService entityValidatorService(){
        return new EntityValidationServiceImpl();
    }

    @Override
    @Bean(name = EntityValueGateService.COMPONENT_NAME)
    public EntityValueGateService entityValueGateService() {
        return new EntityValueGateServiceImpl();
    }


        /* IEntityProtectionBeanList                                        **/
}
