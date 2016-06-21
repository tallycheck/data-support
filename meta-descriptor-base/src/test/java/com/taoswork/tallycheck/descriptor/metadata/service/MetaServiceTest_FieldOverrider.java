package com.taoswork.tallycheck.descriptor.metadata.service;

import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.basic.StringFieldMeta;
import com.taoswork.tallycheck.descriptor.service.MetaService;
import com.taoswork.tallycheck.descriptor.service.impl.GeneralMetaServiceImpl;
import com.taoswork.tallycheck.testmaterial.general.domain.meta.AA;
import com.taoswork.tallycheck.testmaterial.general.domain.meta.AAA;
import com.taoswork.tallycheck.testmaterial.general.domain.meta.AAB;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/11/11.
 */
public class MetaServiceTest_FieldOverrider {
    private MetaService metaService;
    private IClassMeta aacm;
    private IClassMeta aaacm;
    private IClassMeta aabcm;

    @Before
    public void setup() {
        metaService = new GeneralMetaServiceImpl();
        aacm = metaService.generateMeta(AA.class, null, true);
        aaacm = metaService.generateMeta(AAA.class, null, true);
        aabcm = metaService.generateMeta(AAB.class, null, true);
    }

    @After
    public void teardown() {
        metaService = null;
    }

    @Test
    public void testValueValidator() {
        StringFieldMeta aaa1 = (StringFieldMeta)aaacm.getFieldMeta("aa2");
        StringFieldMeta aaa2 = (StringFieldMeta)aabcm.getFieldMeta("aa2");
        Assert.assertEquals(aaa1.getLength(), 1);
        Assert.assertEquals(aaa2.getLength(), 2);
    }

}
