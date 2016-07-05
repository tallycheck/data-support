package com.taoswork.tallycheck.datasolution.jpa.servicemockup;

import com.taoswork.tallycheck.datasolution.IDataSolutionDefinition;

/**
 * Created by Gao Yuan on 2016/2/14.
 */
public class TallyMockupDataSolutionDefinition implements IDataSolutionDefinition {
    //DataServiceName
    public final static String DATA_SOLUTION_NAME = "TallyMockupDataSolution";

    public static final String TMOCKUP_ENTITY_MESSAGES = ENTITY_MESSAGES_FILE_PREFIX +
            "tallymockup/";

    public static final String TMOCKUP_ERROR_MESSAGES = ERROR_MESSAGES_FILE_PREFIX +
            "tallymockup/";

    public static final String TMOCKUP_RUNTIME_PROPERTIES = RUNTIME_PROPERTIES_FILE_PREFIX +
            "tallymockup/";

    @Override
    public String getDataSolutionName() {
        return DATA_SOLUTION_NAME;
    }

    @Override
    public String getEntityMessageDirectory() {
        return TMOCKUP_ENTITY_MESSAGES;
    }

    @Override
    public String getErrorMessageDirectory() {
        return TMOCKUP_ERROR_MESSAGES;
    }

    @Override
    public String getPropertiesResourceDirectory() {
        return TMOCKUP_RUNTIME_PROPERTIES;
    }

    @Override
    public Class[] getExtraConfig() {
        return new Class[]{};
    }
}
