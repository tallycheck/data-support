package com.taoswork.tallycheck.descriptor.description.service;

import com.taoswork.tallycheck.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallycheck.descriptor.service.impl.BaseMetaInfoServiceImpl;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;
import org.junit.After;
import org.junit.Before;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest_CompanyImpl {
    private MetaService metaService;
    private MetaInfoService metaInfoService;

    @Before
    public void setup() {
        metaService = new JpaMetaServiceImpl();
        metaInfoService = new BaseMetaInfoServiceImpl();
    }

    @After
    public void teardown() {
        metaService = null;
        metaInfoService = null;
    }


}
