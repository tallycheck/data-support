package com.taoswork.tallycheck.datasolution;

import com.taoswork.tallycheck.general.extension.collections.StringChain;

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

    public static class Utils {
        public static String FileChain (String prefix, String delimter, String ... nodes){
            StringChain sb = new StringChain();
            sb.setFixes("",delimter,"");
            for (String node : nodes){
                sb.add(prefix + node);
            }

            return sb.toString();
        }

    }

}
