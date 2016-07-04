package com.taoswork.tallycheck.authority.atom.utility;

/**
 * Created by gaoyuan on 7/4/16.
 */
public class ResourceUtility {
    public static String unifiedResourceName(String resourceName){
        if(resourceName.startsWith("#"))
            return resourceName;
        return "#"+resourceName.replaceAll("\\.", "-");
    }

    public static String unifiedResourceName(Class resource){
        return unifiedResourceName(resource.getName());
    }

}
