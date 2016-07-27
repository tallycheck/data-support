package com.taoswork.tallycheck.dataservice.frontend.io.response.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by gaoyuan on 7/20/16.
 */
public class BasicInfo {
    private Class<?> type;
    private Class<?> ceilingType;
    private String idField;
    private String nameField;

    private String beanUri;

    public Class<?> getCeilingType() {
        return ceilingType;
    }

    public BasicInfo setCeilingType(Class<?> ceilingType) {
        this.ceilingType = ceilingType;
        return this;
    }

    public Class<?> getType() {
        return type;
    }

    public BasicInfo setType(Class<?> type) {
        this.type = type;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBeanUri() {
        return beanUri;
    }

    public BasicInfo setBeanUri(String beanUri) {
        this.beanUri = beanUri;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getIdField() {
        return idField;
    }

    public BasicInfo setIdFieldIfEmpty(String idField) {
        if (StringUtils.isEmpty(this.idField)) {
            setIdField(idField);
        }
        return this;
    }

    public BasicInfo setIdField(String idField) {
        this.idField = idField;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getNameField() {
        return nameField;
    }

    public BasicInfo setNameFieldIfEmpty(String nameField) {
        if (StringUtils.isEmpty(this.nameField)) {
            setNameField(nameField);
        }
        return this;
    }

    public BasicInfo setNameField(String nameField) {
        this.nameField = nameField;
        return this;
    }

}
