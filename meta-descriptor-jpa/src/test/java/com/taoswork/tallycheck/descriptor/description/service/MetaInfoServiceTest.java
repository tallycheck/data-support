package com.taoswork.tallycheck.descriptor.description.service;

import com.taoswork.tallycheck.descriptor.description.infos.main.EntityInfo;
import com.taoswork.tallycheck.descriptor.jpa.service.JpaMetaServiceImpl;
import com.taoswork.tallycheck.descriptor.service.impl.BaseMetaInfoServiceImpl;
import com.taoswork.tallycheck.descriptor.metadata.IClassMeta;
import com.taoswork.tallycheck.descriptor.metadata.classtree.EntityClassTree;
import com.taoswork.tallycheck.descriptor.metadata.classtree.EntityClassTreeAccessor;
import com.taoswork.tallycheck.descriptor.service.MetaInfoService;
import com.taoswork.tallycheck.descriptor.service.MetaService;
import com.taoswork.tallycheck.testmaterial.general.domain.meta.A;
import com.taoswork.tallycheck.testmaterial.general.domain.meta.AA;
import com.taoswork.tallycheck.testmaterial.general.domain.meta.AAA;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MetaInfoServiceTest {
    private MetaInfoService metaInfoService;
    private MetaService metaService;

    @Before
    public void setup() {
        metaService = new JpaMetaServiceImpl();
        BaseMetaInfoServiceImpl metaInfoServiceImpl
                = new BaseMetaInfoServiceImpl();
        metaInfoService = metaInfoServiceImpl;
    }

    @After
    public void teardown() {
        metaInfoService = null;
        metaService = null;
    }

    @Test
    public void testClassInfo() {
        EntityClassTreeAccessor accessor = new EntityClassTreeAccessor();
        EntityClassTree classTree = new EntityClassTree(A.class);
        accessor.add(classTree, AA.class);
        accessor.add(classTree, AAA.class);

        IClassMeta classMeta = metaService.generateMeta(classTree, null, true);
        EntityInfo entityInfo = metaInfoService.generateEntityMainInfo(classMeta);
        Assert.assertNotNull(entityInfo);
        Assert.assertEquals(entityInfo.getType(), A.class.getName());
        Assert.assertEquals(entityInfo.getGridFields().size(), 4);
    }
}
