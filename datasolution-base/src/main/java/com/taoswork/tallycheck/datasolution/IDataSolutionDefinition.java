package com.taoswork.tallycheck.datasolution;

/**
 * Created by Gao Yuan on 2016/2/13.
 */
public interface IDataSolutionDefinition {
    public static final String DATA_SOLUTION_DEFINITION_BEAN_NAME = "IDataSolutionDefinition";
    public static final String RUNTIME_PROPERTIES_FILE_PREFIX = "/runtime-properties/";
    public static final String ENTITY_MESSAGES_FILE_PREFIX = "/entity-messages/";
    public static final String ERROR_MESSAGES_FILE_PREFIX = "/error-messages/";

    public static final String FILE_DELIMTER = ";";

    String getDataSolutionName();

    String getEntityMessageDirectory();

    String getErrorMessageDirectory();

    String getPropertiesResourceDirectory();

    Class[] getExtraConfig();

}
