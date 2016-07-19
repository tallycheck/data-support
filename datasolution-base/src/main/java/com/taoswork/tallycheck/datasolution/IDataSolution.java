package com.taoswork.tallycheck.datasolution;

import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.dataservice.EntityType;
import com.taoswork.tallycheck.datasolution.security.AccessInterrupters;
import com.taoswork.tallycheck.datasolution.security.EntityFilterType;

import java.util.Collection;

/**
 * See Definition in :
 * JPADataServiceDefinition
 * ({@link JPADataServiceDefinition})
 *
 * Beans loaded to IDataSolution by DataSolutionBase( ... annotatedConfigurationClasses)
 *
 * Configuration classes:
 * ADataServiceBeanConfiguration
 * ({@link ADataServiceBeanConfiguration})
 */
public interface IDataSolution {
    public static final String DATA_SOLUTION_NAME_S_BEAN_NAME = "DataSolutionBeanName";
    public static final String ERROR_MESSAGE_SOURCE_BEAN_NAME = "ErrorMessageSource";
    public static final String PROPERTY_CONFIGURER = "PROPERTY_CONFIGURER";
    public static final String ACCESS_INTERRUPTERS = "ACCESS_INTERRUPTERS";

    String getName();

    <T> T getService(String serviceName);

    <T> T getService(Class<T> clz, String serviceName);

    <T> T getService(Class<T> serviceCls);

    IDataSolutionDefinition getDataSolutionDefinition();

    Collection<String> getEntityTypeNames();

    EntityType getEntityType(String entityTypeName);

    Collection<EntityFilterType> getEntityFilterTypes(String entityTypeName);

    /**
     * Type name to EntityType
     * @return
     */
    Collection<EntityType> getEntityTypes();

    /**
     * Convert typeName to resource
     * com.tallybook.data.Person -> person
     * com.tallybook.data.Tool -> tool
     *
     * @param typeName, the result of type.getName()
     * @return the resource name
     */
    String getEntityResourceName(String typeName);

    /**
     * Convert resource to typeName
     * person -> com.tallybook.data.Person
     * tool -> com.tallybook.data.Tool
     *
     * @param resourceName, the resource name
     * @return the type name
     */
    String getEntityTypeName(String resourceName);

    void setAuthorityProvider(IAuthorityProvider provider);

}
