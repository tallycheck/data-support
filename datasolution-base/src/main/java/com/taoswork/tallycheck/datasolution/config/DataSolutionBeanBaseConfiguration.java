package com.taoswork.tallycheck.datasolution.config;

import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.dataservice.IDataService;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.IDataSolutionDefinition;
import com.taoswork.tallycheck.datasolution.IDataSolutionDelegate;
import com.taoswork.tallycheck.datasolution.config.beanlist.IDataSolutionSupporterBeanList;
import com.taoswork.tallycheck.datasolution.config.beanlist.IEntityProtectionBeanList;
import com.taoswork.tallycheck.datasolution.config.beanlist.IGeneralBeanList;
import com.taoswork.tallycheck.datasolution.config.helper.DataSolutionBeanCreationHelper;
import com.taoswork.tallycheck.datasolution.core.description.FriendlyMetaInfoService;
import com.taoswork.tallycheck.datasolution.core.description.impl.FriendlyMetaInfoServiceImpl;
import com.taoswork.tallycheck.datasolution.security.ISecurityVerifier;
import com.taoswork.tallycheck.datasolution.security.impl.SecurityVerifierOnAuthority;
import com.taoswork.tallycheck.datasolution.service.EntityValidationService;
import com.taoswork.tallycheck.datasolution.service.EntityValueGateService;
import com.taoswork.tallycheck.datasolution.service.impl.BasicDataService;
import com.taoswork.tallycheck.datasolution.service.impl.EntityValidationServiceImpl;
import com.taoswork.tallycheck.datasolution.service.impl.EntityValueGateServiceImpl;
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
public abstract class DataSolutionBeanBaseConfiguration
    implements
        ApplicationContextAware,
        IGeneralBeanList,
        IDataSolutionSupporterBeanList,
        IEntityProtectionBeanList
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSolutionBeanBaseConfiguration.class);

    private IDataSolutionDefinition dsDefinition;
    protected DataSolutionBeanCreationHelper helper;

    // **************************************************** //
    //  Constructor & initialize                            //
    // **************************************************** //

    public DataSolutionBeanBaseConfiguration() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof IDataSolutionDelegate) {
            IDataSolutionDelegate delegate = ((IDataSolutionDelegate) applicationContext);
            IDataSolutionDefinition dataServiceDefinition = delegate.getDataSolutionDefinition();
            this.dsDefinition = dataServiceDefinition;
            helper = new DataSolutionBeanCreationHelper(dsDefinition);
        } else {
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
        return new BeanCreationMonitor(dsDefinition.getDataSolutionName());
    }

    @Override
    @Bean(name = IDataSolution.DATA_SOLUTION_NAME_S_BEAN_NAME)
    public String dataSolutionName() {
        return dsDefinition.getDataSolutionName();
    }

    /* IGeneralBeanList                                        **/

    /************************************************************
     * IDataSolutionSupporterBeanList                            *
     * ({@link IDataSolutionSupporterBeanList})
     ************************************************************/

    @Override
    @Bean(name = IDataSolution.PROPERTY_CONFIGURER)
    public RuntimeEnvironmentPropertyPlaceholderConfigurer runtimeEnvironmentPropertyPlaceholderConfigurer() {
        return helper.createDefaultRuntimeEnvironmentPropertyPlaceholderConfigurer();
    }

    @Override
    @Bean(name = FriendlyMetaInfoService.MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource entityMessageSource(){
        return helper.createEntityMessageSource();
    }

    @Override
    @Bean(name = IDataSolution.ERROR_MESSAGE_SOURCE_BEAN_NAME)
    public MessageSource errorMessageSource() {
        return helper.createErrorMessageSource();
    }

    @Override
    @Bean(name = FriendlyMetaInfoService.SERVICE_NAME)
    public FriendlyMetaInfoService friendlyMetaInfoService(){
        return new FriendlyMetaInfoServiceImpl();
    }


    @Override
    @Bean(name = EntityFilterManager.COMPONENT_NAME)
    public EntityFilterManager entityFilterManager(){
        return new EntityFilterManager();
    }

    @Override
    @Bean(name = ISecurityVerifier.COMPONENT_NAME)
    public ISecurityVerifier securityVerifier() {
        return new SecurityVerifierOnAuthority();
    }
    /* IDataSolutionSupporterBeanList                            */

    @Override
    @Bean(name = BasicDataService.COMPONENT_NAME)
    public IDataService dataService() {
        return new BasicDataService();
    }

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
