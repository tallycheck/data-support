package com.taoswork.tallycheck.datasolution.jpa.config;

import com.taoswork.tallycheck.datasolution.config.DataSolutionBeanBaseConfiguration;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.IEntityTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gaoyuan on 7/9/16.
 */
@Configuration
public class JpaSolutionConfiguration extends DataSolutionBeanBaseConfiguration {

    @Bean(name = IEntityTranslator.COMPONENT_NAME)
    @Override
    public IEntityTranslator entityTranslator() {
        return new EntityTranslator();
    }
}
