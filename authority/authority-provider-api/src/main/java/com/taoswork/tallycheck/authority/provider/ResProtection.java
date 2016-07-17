package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.ProtectionMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gaoyuan on 7/2/16.
 */
public class ResProtection implements Serializable {
    public String resource;
    public boolean masterControlled;
    public ProtectionMode protectionMode;
    public Collection<ResProtectionCase> cases;
    public long version;

    public void addCases(ResProtectionCase ... _cases){
        if(cases == null){
            cases = new ArrayList<ResProtectionCase>();
        }
        for (ResProtectionCase _case : _cases){
            cases.add(_case);
        }
    }
}
