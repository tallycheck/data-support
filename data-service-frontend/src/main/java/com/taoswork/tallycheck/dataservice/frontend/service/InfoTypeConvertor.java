package com.taoswork.tallycheck.dataservice.frontend.service;

import com.taoswork.tallycheck.dataservice.InfoType;
import com.taoswork.tallycheck.descriptor.description.infos.EntityInfoType;
import com.taoswork.tallycheck.general.extension.IllegalCodePathException;

/**
 * Created by gaoyuan on 7/8/16.
 */
public class InfoTypeConvertor {
    public static InfoType convert(EntityInfoType infoType){
        switch (infoType){
            case Main:
                return InfoType.Main;
            case Full:
                return InfoType.Full;
            case Grid:
                return InfoType.Grid;
            case Form:
                return InfoType.Form;
            default:
                throw new IllegalCodePathException();
        }
    }
    public static EntityInfoType convert(InfoType infoType){
        switch (infoType){
            case Main:
                return EntityInfoType.Main;
            case Full:
                return EntityInfoType.Full;
            case Grid:
                return EntityInfoType.Grid;
            case Form:
                return EntityInfoType.Form;
            default:
                throw new IllegalCodePathException();
        }
    }
}
