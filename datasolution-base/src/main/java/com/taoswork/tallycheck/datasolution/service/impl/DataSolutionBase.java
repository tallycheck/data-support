package com.taoswork.tallycheck.datasolution.service.impl;

import com.taoswork.tallycheck.dataservice.EntityType;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.IDataSolutionDefinition;
import com.taoswork.tallycheck.datasolution.IDataSolutionDelegate;
import com.taoswork.tallycheck.datasolution.config.DataSolutionBeanBaseConfiguration;
import com.taoswork.tallycheck.datasolution.security.EntityFilterType;
import com.taoswork.tallycheck.datasolution.security.ISecurityVerifier;
import com.taoswork.tallycheck.datasolution.security.impl.SecurityVerifierAgent;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.general.extension.utils.StringUtility;
import com.taoswork.tallycheck.general.solution.cache.ehcache.CachedRepoManager;
import com.taoswork.tallycheck.general.solution.time.TimeCounter;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/5/11.
 */
public abstract class DataSolutionBase implements IDataSolution {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSolutionBase.class);
    private static final Map<String, Integer> loadCounter = new HashMap<String, Integer>();

    private ApplicationContext applicationContext;
    private final IDataSolutionDefinition dsDef;

    private final Map<String, String> entityResNameToTypeName = new HashMap<String, String>();
    //key is type name
    private final Map<String, EntityType> entityTypeNameToTypes = new HashMap<String, EntityType>();
    private Collection<EntityType> entityTypes;

    private final MultiMap<String, EntityFilterType> filterTypeMap = new MultiValueMap<String, EntityFilterType>();

    public DataSolutionBase(
            IDataSolutionDefinition dsDef,
            List<Class> annotatedClasses) {
        TimeCounter counter = new TimeCounter();
        this.dsDef = dsDef;
        List<Class> annotatedClassesList = new ArrayList<Class>();

        annotatedClassesList.add(DataSolutionBeanBaseConfiguration.class);

        if (annotatedClasses != null) {
            for (Class ac : annotatedClasses) {
                annotatedClassesList.add(ac);
            }
        }

        Class[] dbRelatedConf = this.dsDef.getExtraConfig();
        if (dbRelatedConf != null) {
            for (Class ac : dbRelatedConf) {
                annotatedClassesList.add(ac);
            }
        }

        load(annotatedClassesList);
        double passedSeconds = counter.getPassedInSeconds();
        LOGGER.info("Load DataSolution {} cost {} seconds.", dsDef.getDataSolutionName(), passedSeconds);
    }


    private void load(
            List<Class> annotatedClasses) {
        String clzName = this.getClass().getName();
        int oldCount = MapUtils.getIntValue(loadCounter, clzName, 0);
        loadCounter.put(clzName, oldCount + 1);

        loadAnnotatedClasses(annotatedClasses.toArray(new Class[annotatedClasses.size()]));
    }

    private void loadAnnotatedClasses(Class<?>... annotatedClasses) {
        try {
            onServiceStart();

            class DataSolutionInsideAnnotationConfigApplicationContext
                    extends AnnotationConfigApplicationContext
                    implements IDataSolutionDelegate {
                private IDataSolution dataService = null;

                @Override
                protected void onClose() {
                    super.onClose();
                    onServiceStop();
                }

                @Override
                public IDataSolutionDefinition getDataSolutionDefinition() {
                    return dsDef;
                }

                @Override
                public IDataSolution theDataSolution() {
                    return dataService;
                }

                public void setDataService(IDataSolution dataService) {
                    this.dataService = dataService;
                }
            }

            DataSolutionInsideAnnotationConfigApplicationContext annotationConfigApplicationContext = new DataSolutionInsideAnnotationConfigApplicationContext();

            annotationConfigApplicationContext.setDisplayName(this.getClass().getSimpleName());
            annotationConfigApplicationContext.register(annotatedClasses);
            annotationConfigApplicationContext.refresh();

            annotationConfigApplicationContext.setDataService(this);

//        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(annotatedClasses);
//        annotationConfigApplicationContext.setDisplayName(this.getClass().getSimpleName());
            applicationContext = annotationConfigApplicationContext;
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            postConstruct();
            postLoadCheck();
        }
    }

    protected void postConstruct() {
    }

    private void postLoadCheck() {
//        DynamicEntityDao dynamicEntityDao = getService(DynamicEntityDao.COMPONENT_NAME);
//        if (dynamicEntityDao == null) {
//            LOGGER.error("Bean '{}' not defined, dynamic entity service may not work.", DynamicEntityDao.COMPONENT_NAME);
//        }

        EntityMetaAccess entityMetaAccess = getService(EntityMetaAccess.COMPONENT_NAME);
        if (entityMetaAccess == null) {
            LOGGER.error("Bean '{}' not defined, dynamic entity service may not work.", EntityMetaAccess.COMPONENT_NAME);
        }
    }

    protected void onServiceStart() {
        CachedRepoManager.startEhcache();
    }

    protected void onServiceStop() {
        CachedRepoManager.stopEhcache();
    }

    @Override
    public String getName() {
        return applicationContext == null ?
                null : (String) applicationContext.getBean(IDataSolution.DATA_SOLUTION_NAME_S_BEAN_NAME);
    }

    @Override
    public <T> T getService(String serviceName) {
        return applicationContext == null ?
                null : (T) applicationContext.getBean(serviceName);
    }

    @Override
    public <T> T getService(Class<T> clz, String serviceName) {
        return applicationContext == null ?
                null : (T) applicationContext.getBean(serviceName);
    }

    @Override
    public <T> T getService(Class<T> serviceCls) {
        Field[] fields = serviceCls.getDeclaredFields();
        int matchingField = 0;
        Field fitFiled = null;
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            boolean isFieldFit = field.getType().equals(String.class) &&
                    Modifier.isStatic(modifiers) &&
                    Modifier.isPublic(modifiers) &&
                    Modifier.isFinal(modifiers);
            if (isFieldFit) {
                matchingField++;
                fitFiled = field;
            }
        }

        String serviceName = "";//serviceCls.getSimpleName();
        try {
            if (matchingField == 1) {
                serviceName = (String) fitFiled.get(null);
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e2) {
        }

        String determinBeanName = "";

        NoSuchBeanDefinitionException noSuchBeanDefinitionException = null;

        if (!serviceName.equals("")) {
            if (applicationContext.containsBean(serviceName)) {
                determinBeanName = serviceName;
            } else {
                String clzName = serviceCls.getSimpleName();
                serviceName = clzName;
                if (applicationContext.containsBean(clzName)) {
                    determinBeanName = clzName;
                } else {
                    clzName = StringUtility.changeFirstCharUpperLowerCase(clzName);
                    determinBeanName = clzName;
                }
            }
        }

        return getService(determinBeanName);
    }

    @Override
    public IDataSolutionDefinition getDataSolutionDefinition() {
        return getService(IDataSolutionDefinition.DATA_SOLUTION_DEFINITION_BEAN_NAME);
    }

    protected void registerEntityFilterType(EntityFilterType filterType){
        this.filterTypeMap.put(filterType.resourceTypeName, filterType);
    }

    @Override
    public Collection<EntityFilterType> getEntityFilterTypes(String entityTypeName) {
        Collection<EntityFilterType> filterTypes = (Collection<EntityFilterType>) this.filterTypeMap.get(entityTypeName);
        return filterTypes;
    }

    private void ensureEntityTypeMappingBuilt(){
        if (entityTypes == null) {
            entityTypeNameToTypes.clear();
            entityResNameToTypeName.clear();
            String dsName = this.getName();

            EntityMetaAccess entityMetaAccess = getService(EntityMetaAccess.COMPONENT_NAME);

            for (Class entityCls : entityMetaAccess.getAllEntities(true, true)) {
                String typeName = entityCls.getName();
                if (entityTypeNameToTypes.containsKey(typeName)) {
                    LOGGER.error("EntityType with name '{}' already exist, over-writing", typeName);
                }
                EntityType entityType = new EntityType(dsName, entityCls);
                String newResourceName = entityType.getResourceName();

                entityTypeNameToTypes.put(typeName, entityType);
                entityResNameToTypeName.put(newResourceName, typeName);
            }
            entityTypes = Collections.unmodifiableCollection(entityTypeNameToTypes.values());
        }
    }

    @Override
    public Collection<String> getEntityTypeNames() {
        ensureEntityTypeMappingBuilt();
        return Collections.unmodifiableCollection(entityTypeNameToTypes.keySet());
    }

    @Override
    public EntityType getEntityType(String entityTypeName) {
        ensureEntityTypeMappingBuilt();
        return entityTypeNameToTypes.get(entityTypeName);
    }

    @Override
    public Collection<EntityType> getEntityTypes(){
        ensureEntityTypeMappingBuilt();
        return entityTypes;
    }

    @Override
    public String getEntityResourceName(String typeName) {
        //Make sure entityType Map built
        getEntityTypes();
        Map<String, EntityType> entityEntries = entityTypeNameToTypes;
        EntityType entityCatalog = entityEntries.get(typeName);
        if (entityCatalog == null) {
            return null;
        }
        return entityCatalog.getResourceName();
    }

    @Override
    public String getEntityTypeName(String resourceName) {
        return entityResNameToTypeName.get(resourceName);
    }

    @Override
    public void setSecurityVerifier(ISecurityVerifier securityVerifier) {
        SecurityVerifierAgent securityVerifierAgent =
                getService(SecurityVerifierAgent.COMPONENT_NAME);
        securityVerifierAgent.setVerifier(securityVerifier);
    }
}
