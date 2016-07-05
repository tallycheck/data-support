package com.taoswork.tallycheck.datasolution.mongo.core.entityservice;

import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.TranslateException;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.ZooKeeper;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.bson.types.ObjectId;
import org.junit.Assert;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityCreateHelper {
    public static int createPeopleEntityWith(IDataSolution dataService,
                                             String namePrefix, int postfixStartingIndex, int createAttempt) {
        EntityTranslator translator = new EntityTranslator();
        MongoEntityService jpaEntityService = dataService.getService(IEntityService.COMPONENT_NAME);
        EntityMetaAccess metaAccess = dataService.getService(EntityMetaAccess.COMPONENT_NAME);
        int created = 0;
        try {
            for (int i = 0; i < createAttempt; ++i) {
                String name = namePrefix + (int) (postfixStartingIndex + i);

//                    int expected = i + 1;
                Entity adminEntity = new Entity();
                adminEntity
                        .setType(ZooKeeperImpl.class)
                        .setProperty("name", name);
                ZooKeeper adminP = (ZooKeeper)translator.translate(metaAccess, adminEntity, null);
                PersistableResult<ZooKeeper> adminRes = jpaEntityService.create(adminP);
                ZooKeeper admin = adminRes.getValue();

                ObjectId id = admin.getId();
                PersistableResult<ZooKeeper> adminFromDbRes = jpaEntityService.read(ZooKeeper.class, id);
                PersistableResult<ZooKeeperImpl> admin2FromDbRes = jpaEntityService.read(ZooKeeperImpl.class, id);

                ZooKeeper adminFromDb = adminFromDbRes.getValue();
                ZooKeeper admin2FromDb = admin2FromDbRes.getValue();

                Assert.assertEquals("Created and Read should be same: " + admin.getId() + " : " + adminFromDb.getId(),
                        admin.getId(), adminFromDb.getId());
//                    Assert.assertTrue("Created Object [" + admin.getId() + "] should have Id: " + expected, admin.getId().equals(0L + expected));

                Assert.assertEquals(admin.getUuid(), adminFromDb.getUuid());
                Assert.assertEquals(adminFromDb.getUuid(), admin2FromDb.getUuid());

                Assert.assertEquals(admin.getId(), adminFromDb.getId());
                Assert.assertEquals(adminFromDb.getId(), admin2FromDb.getId());

                Assert.assertNotNull(adminFromDb);
                Assert.assertTrue(adminFromDb.getName().equals(name));

                created++;
            }
        } catch (ServiceException exp) {
            Assert.fail();
        } catch (TranslateException e) {
            Assert.fail();
        } finally {
            Assert.assertEquals(createAttempt, created);
        }
        return created;
    }
}
