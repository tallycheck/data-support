package com.taoswork.tallycheck.testmaterial.jpa;

import com.taoswork.tallycheck.testmaterial.general.TestSetting;

/**
 * Created by Gao Yuan on 2015/10/20.
 */
public class JpaTestSetting extends TestSetting {


    public static boolean useMysql(){
        String use = properties.getProperty("use.mysql", "true");
        return valueInBoolean(use);
    }
}
