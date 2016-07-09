package com.taoswork.tallycheck.info;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by gaoyuan on 7/7/16.
 */
public class InfoException extends RuntimeException {
    public InfoException() {
    }

    public InfoException(String message) {
        super(message);
    }

    public InfoException(String message, Throwable cause) {
        super(message, cause);
    }

    public InfoException(Throwable cause) {
        super(cause);
    }

    public static void throwFieldConfigurationException(Field field, String message) {
        if(StringUtils.isEmpty(message)){
            throw new InfoException("Field configuration error: " + field);
        }else {
            throw new InfoException("Field configuration error, [" + message +"] : " + field);
        }
    }

    public static void throwFieldConfigurationException(Field field){
        throwFieldConfigurationException(field, "");
    }

}
