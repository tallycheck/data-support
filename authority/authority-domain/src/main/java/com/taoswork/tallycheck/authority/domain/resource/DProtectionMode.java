package com.taoswork.tallycheck.authority.domain.resource;

import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.general.extension.IllegalCodePathException;
import com.taoswork.tallycheck.general.extension.utils.IFriendlyEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/3/16.
 */
public enum DProtectionMode implements IFriendlyEnum<String> {
    FitAny("any", "Any"),
    FitAll("all", "All");

    private final String type;
    private final String friendlyType;

    private static final Map<String, DProtectionMode> typeToEnum = new HashMap<String, DProtectionMode>();
    static {
        for(DProtectionMode _enum : values()){
            typeToEnum.put(_enum.type, _enum);
        }
    }

    DProtectionMode(String type, String friendlyType) {
        this.type = type;
        this.friendlyType = friendlyType;
    }

    public static DProtectionMode fromType(String character){
        return typeToEnum.get(character);
    }

    public static String toType(DProtectionMode protectionMode){
        return protectionMode.getType();
    }

    public String getType() {
        return type;
    }

    public String getFriendlyType() {
        return friendlyType;
    }

    public static ProtectionMode toNativeType(DProtectionMode pmode){
        switch (pmode){
            case FitAll:
                return ProtectionMode.FitAll;
            case FitAny:
                return ProtectionMode.FitAny;
            default:
                throw new IllegalCodePathException();
        }
    }

    public static DProtectionMode fromNativeType(ProtectionMode pmode){
        switch (pmode){
            case FitAll:
                return DProtectionMode.FitAll;
            case FitAny:
                return DProtectionMode.FitAny;
            default:
                throw new IllegalCodePathException();
        }
    }

}
