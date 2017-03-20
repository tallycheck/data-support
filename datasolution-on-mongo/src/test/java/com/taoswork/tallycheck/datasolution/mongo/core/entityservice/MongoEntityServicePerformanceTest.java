package com.taoswork.tallycheck.datasolution.mongo.core.entityservice;

import com.taoswork.tallycheck.authority.provider.AllPassAuthorityProvider;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.query.PropertyFilterCriteria;
import com.taoswork.tallycheck.datasolution.DatasourceDefinition;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.servicemockup.TallyMockupMongoDataSolution;
import com.taoswork.tallycheck.datasolution.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.service.EasyEntityService;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.descriptor.dataio.in.Entity;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.EntityTranslator;
import com.taoswork.tallycheck.descriptor.dataio.in.translator.TranslateException;
import com.taoswork.tallycheck.general.solution.time.MethodTimeCounter;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.ZooKeeper;
import com.taoswork.tallycheck.testmaterial.mongo.domain.zoo.impl.ZooKeeperImpl;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Gao Yuan on 2015/11/10.
 */
public class MongoEntityServicePerformanceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoEntityServicePerformanceTest.class);

    private IDataSolution dataSolution = null;
    private EntityTranslator translator = null;
    private EntityMetaAccess metaAccess = null;
    private SecurityAccessor accessor = new SecurityAccessor();
    private Operator operator = new Operator();

    @Before
    public void setup() {
        dataSolution = new TallyMockupMongoDataSolution();
        dataSolution.setAuthorityProvider(new AllPassAuthorityProvider());

        metaAccess = dataSolution.getService(EntityMetaAccess.COMPONENT_NAME);
        translator = new EntityTranslator();
    }

    @After
    public void teardown() {
        TallyMockupMongoDatasourceConfiguration.DatasourceDefinition mdbDef = dataSolution.getService(DatasourceDefinition.DATA_SOURCE_DEFINITION);
        mdbDef.dropDatabase();
        dataSolution = null;
        metaAccess = null;
        translator = null;
    }

    @Test
    public void testCRUDQ() throws ServiceException {
        int loopCount = 20;
        int inLoopAttempt = 20;
        EasyEntityService easyEntityService = new EasyEntityService(dataSolution);
        IEntityService entityService = dataSolution.getService(IEntityService.COMPONENT_NAME);

        Set<ObjectId> ids = new HashSet<ObjectId>();
        final int total;
        try {
            int created = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "CREATE");
            for (int l = 0; l < loopCount; ++l) {
                String nameAAA = entityPrefix(l);
                for (int i = 0; i < inLoopAttempt; ++i) {
                    Entity adminEntity = new Entity();
                    adminEntity
                            .setType(ZooKeeperImpl.class)
                            .setProperty("name", nameAAA + i);
                    ZooKeeper adminP = (ZooKeeper)translator.translate(metaAccess, adminEntity, null);
                    PersistableResult<ZooKeeper> adminRes = entityService.create(operator, accessor, adminP);
                    ZooKeeper admin = adminRes.getValue();
                    ids.add(admin.getId());
                    created++;
                }
            }
            Assert.assertEquals(loopCount * inLoopAttempt, ids.size());
            Assert.assertEquals(loopCount * inLoopAttempt, created);
            total = created;
            methodTimeCounter.noticePerActionCostOnExit(created);
        } catch (TranslateException e) {
            throw new ServiceException(e);
        }
        {
            int query = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "QUERY SINGLE");
            for (ObjectId id : ids) {
                CriteriaTransferObject cto = new CriteriaTransferObject();
                cto.addFilterCriteria(new PropertyFilterCriteria("id", "" + id));
                CriteriaQueryResult<ZooKeeper> zooKeepers = easyEntityService.query(accessor, ZooKeeper.class, cto);
                query++;
            }
            methodTimeCounter.noticePerActionCostOnExit(query);
        }
        {
            int query = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "QUERY MULTIPLE");
            for (int l = 0; l < loopCount; ++l) {
                String nameAAA = entityPrefix(l);
                for (int i = 0; i < inLoopAttempt; ++i) {
                    CriteriaTransferObject cto = new CriteriaTransferObject();
                    cto.addFilterCriteria(new PropertyFilterCriteria("name", nameAAA));
                    CriteriaQueryResult<ZooKeeper> zooKeepers = easyEntityService.query(accessor, ZooKeeper.class, cto);
                    query++;
                }
            }
            methodTimeCounter.noticePerActionCostOnExit(query);
        }
        {
            int read = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "READ");
            for (ObjectId id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = easyEntityService.read(operator, accessor, ZooKeeper.class, (id));
                Assert.assertEquals(id, adminFromDbRes.getValue().getId());
                read++;
            }
            methodTimeCounter.noticePerActionCostOnExit(read);
        }
        {
            int updated = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "UPDATE");
            for (ObjectId id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = easyEntityService.read(operator, accessor, ZooKeeper.class, (id));
                ZooKeeper admin = adminFromDbRes.getValue();
                String oldEmail = admin.getEmail();
                String newEmail = admin.getName() + "@xxx.com";
                admin.setEmail(newEmail);
                PersistableResult<ZooKeeper> freshAdminFrom = entityService.update(operator, accessor, admin);
                Assert.assertEquals(newEmail, freshAdminFrom.getValue().getEmail());
                updated++;
            }
            methodTimeCounter.noticePerActionCostOnExit(updated);
        }
        {
            int deleted = 0;
            MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER, "DELETE");
            for (ObjectId id : ids) {
                PersistableResult<ZooKeeper> adminFromDbRes = easyEntityService.read(operator, accessor, ZooKeeper.class, (id));
                ZooKeeperImpl zk = new ZooKeeperImpl();
                zk.setId(id);
                boolean delOk = easyEntityService.delete(operator, accessor, zk);
                Assert.assertTrue(delOk);
                deleted++;
            }
            methodTimeCounter.noticePerActionCostOnExit(deleted);
        }

    }

    public String entityPrefix(int i) {
        return "name" + i + "_";
    }

}
