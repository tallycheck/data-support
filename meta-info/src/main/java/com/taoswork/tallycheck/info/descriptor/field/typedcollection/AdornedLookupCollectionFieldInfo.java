package com.taoswork.tallycheck.info.descriptor.field.typedcollection;

/**
 * Created by Gao Yuan on 2015/11/15.
 */
public class AdornedLookupCollectionFieldInfo extends _CollectionFieldInfo {

    public AdornedLookupCollectionFieldInfo() {
    }

    public AdornedLookupCollectionFieldInfo(String name, String friendlyName, boolean editable, String instanceType) {
        super(name, friendlyName, editable, instanceType);
    }

    @Override
    public String getEntryType() {
        return "entity-ref-adorned";
    }
}
