package com.taoswork.tallycheck.info.descriptor.field.typedcollection;

import com.taoswork.tallycheck.datadomain.base.restful.CollectionAction;
import com.taoswork.tallycheck.datadomain.base.restful.EntityActionPaths;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/11/15.
 */

public class BasicCollectionFieldInfo extends _CollectionFieldInfo {
    /**
     * Potential supported actions:
     * create
     */
    private final static Collection<String> supportedActions;

    static {
        Set<CollectionAction> tempActions = new HashSet<CollectionAction>();
        tempActions.add(CollectionAction.CREATE);
        tempActions.add(CollectionAction.READ);
        tempActions.add(CollectionAction.UPDATE);
        tempActions.add(CollectionAction.DELETE);
        tempActions.add(CollectionAction.QUERY);
//        tempActions.add(CollectionAction.REORDER);

        supportedActions = CollectionAction.convertToTypes(tempActions, new HashSet<String>());
    }

    private Map<String, String> actionRefUrls;

    public BasicCollectionFieldInfo() {
    }

    /**
     * @param name
     * @param friendlyName
     * @param editable
     * @param instanceType
     */
    public BasicCollectionFieldInfo(String name, String friendlyName, boolean editable, String instanceType) {
        super(name, friendlyName, editable, instanceType);
        Map<String, String> actionRefUrlsTemp = new HashMap<String, String>();
        for (String actionStr : supportedActions) {
            CollectionAction action = CollectionAction.fromType(actionStr);
            String relUrl = EntityActionPaths.BeanFieldUris.uriTemplateForCollectionAction(name, action);
            actionRefUrlsTemp.put(actionStr, relUrl);
        }
        actionRefUrls = Collections.unmodifiableMap(actionRefUrlsTemp);
    }

    @Override
    public String getEntryType() {
        return "basic";
    }

    public Collection<String> getActions() {
        return supportedActions;
    }

    public Map<String, String> getLinks() {
        return actionRefUrls;
    }
}
