package com.taoswork.tallycheck.info.descriptor.field.typed;

/**
 * Created by Gao Yuan on 2015/10/24.
 */
public class ExternalForeignKeyFieldInfo extends ForeignKeyFieldInfo {

    public String entityFieldName;

    public ExternalForeignKeyFieldInfo() {
    }

    public ExternalForeignKeyFieldInfo(String name, String friendlyName, boolean editable,
                                       Class declareType, Class entityType, String entityFieldName,
                                       String entityFieldIdProperty,
                                       String entityFieldDisplayProperty) {
        super(name, friendlyName, editable, declareType, entityType,
                entityFieldIdProperty,
                entityFieldDisplayProperty);
        this.entityFieldName = entityFieldName;
    }
}
