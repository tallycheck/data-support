package com.taoswork.tallycheck.dataservice.exception;

import com.taoswork.tallycheck.dataservice.PersistableResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/9/28.
 */
public class EntityValidationException extends ServiceException {
    private final PersistableResult entity;
    private final List<String> errors = new ArrayList<String>();
    private final EasyMultiValueMap<String, String> fieldErrors = new EasyMultiValueMap<String, String>();

    public EntityValidationException(PersistableResult entity) {
        this.entity = entity;
    }

    public PersistableResult getEntity() {
        return entity;
    }

    public void addError(String message){
        errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void addFieldError(String field, String message){
        fieldErrors.add(field, message);
    }

    public Collection<String> getErrorFields(){
        return fieldErrors.getKeys();
    }

    public Collection<String> getFieldErrors(String field){
        return fieldErrors.getAll(field);
    }
}
