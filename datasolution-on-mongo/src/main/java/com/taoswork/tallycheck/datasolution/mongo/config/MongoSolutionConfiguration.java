package com.taoswork.tallycheck.datasolution.mongo.config;

import com.taoswork.tallycheck.datasolution.config.DataSolutionBeanBaseConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.core.convertors.ObjectIdConverter;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.IEntityTranslator;
import org.apache.commons.beanutils.ConvertUtilsBean2;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by gaoyuan on 7/9/16.
 */
@Configuration
public class MongoSolutionConfiguration extends DataSolutionBeanBaseConfiguration {

    @Bean(name = IEntityTranslator.COMPONENT_NAME)
    @Override
    public IEntityTranslator entityTranslator() {
        return new EntityTranslator(){
            @Override
            protected ConvertUtilsBean2 makeConvertUtils() {
                ConvertUtilsBean2 convertUtilsBean2 = super.makeConvertUtils();
                convertUtilsBean2.register(new ObjectIdConverter(), ObjectId.class);
                return convertUtilsBean2;
            }
        };
    }
}
