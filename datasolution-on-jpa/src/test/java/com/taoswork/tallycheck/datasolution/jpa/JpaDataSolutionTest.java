package com.taoswork.tallycheck.datasolution.jpa;

import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.jpa.config.db.TestDbConfig;
import com.taoswork.tallycheck.datasolution.jpa.servicemockup.TallyMockupDataSolution;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.testmaterial.jpa.domain.zoo.ZooKeeper;
import com.taoswork.tallycheck.testmaterial.jpa.domain.zoo.impl.ZooKeeperImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2015/10/2.
 */
public class JpaDataSolutionTest {

    private IDataSolution dataService = null;

    @Before
    public void setup(){
        dataService = new TallyMockupDataSolution(TestDbConfig.class);
    }

    @After
    public void teardown(){
        dataService = null;
    }

    @Test
    public void testEntityEntries(){
        {
            Class entityType = ZooKeeper.class;
            String personResName = dataService.getEntityResourceName(entityType.getName());
            String personTypeName = dataService.getEntityTypeName(personResName);

            Assert.assertEquals(personResName, entityType.getSimpleName().toLowerCase());
            Assert.assertEquals(personTypeName, entityType.getName());
            Assert.assertNotNull(dataService.getEntityType(personTypeName));
        }
        {
            Class entityType = ZooKeeperImpl.class;
            String personResName = dataService.getEntityResourceName(entityType.getName());
            String personTypeName = dataService.getEntityTypeName(personResName);

            Assert.assertEquals(personResName, entityType.getSimpleName().toLowerCase());
            Assert.assertEquals(personTypeName, entityType.getName());
            Assert.assertNotNull(dataService.getEntityType(personTypeName));
        }

        EntityMetaAccess entityMetaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        Assert.assertNotNull(entityMetaAccess);

        Collection<Class> entities = entityMetaAccess.getAllEntities(true, true);
        for(Class entity : entities){
            Assert.assertNotNull(dataService.getEntityType(entity.getName()));
        }

        Assert.assertEquals(entities.size(), dataService.getEntityTypes().size());
        Assert.assertEquals(entities.size(), dataService.getEntityTypeNames().size());
    }

}
