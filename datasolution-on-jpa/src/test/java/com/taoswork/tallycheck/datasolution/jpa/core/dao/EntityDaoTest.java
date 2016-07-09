package com.taoswork.tallycheck.datasolution.jpa.core.dao;

import com.taoswork.tallycheck.authority.provider.AllPassAuthorityProvider;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.jpa.config.db.TestDbConfig;
import com.taoswork.tallycheck.datasolution.jpa.core.entityservice.EntityCreateHelper;
import com.taoswork.tallycheck.datasolution.jpa.servicemockup.TallyMockupDataSolution;
import com.taoswork.tallycheck.general.solution.time.MethodTimeCounter;
import com.taoswork.tallycheck.testmaterial.jpa.domain.zoo.impl.ZooKeeperImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Gao Yuan on 2015/9/23.
 */
public class EntityDaoTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityDaoTest.class);

    private IDataSolution dataSolution = null;

    @Before
    public void setup() {
        dataSolution = new TallyMockupDataSolution(TestDbConfig.class);
        dataSolution.setAuthorityProvider(new AllPassAuthorityProvider());
//        dataSolution.setAuthorityContext(new ProtectedAccessContext());
    }

    @After
    public void teardown() {
        dataSolution = null;
    }

    @Test
    public void testDynamicEntityDao() throws ServiceException {
        MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER);

//        JpaEntityService entityService = dataSolution.getService(IEntityService.COMPONENT_NAME);
        EntityDao entityDao = dataSolution.getService(EntityDao.COMPONENT_NAME);
        Assert.assertNotNull(entityDao);

        String nameFieldName = "name";

        int created = 0;
        String nameAAA = "aaa";
        int createAttemptA = 10;

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeperImpl> persons = entityDao.query(ZooKeeperImpl.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), 0);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
        }


        created += EntityCreateHelper.createPeopleEntityWith(dataSolution, nameAAA, created, createAttemptA);

        Assert.assertTrue(created == (createAttemptA));

        {
            //Test count all
            CriteriaTransferObject ctoFetchAll = new CriteriaTransferObject();
            CriteriaQueryResult<ZooKeeperImpl> persons = entityDao.query(ZooKeeperImpl.class, ctoFetchAll);

            Assert.assertEquals(persons.fetchedCount(), createAttemptA);
            Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
            Assert.assertEquals(persons.getStartIndex(), 0);

            for (int skipLeading = 2; skipLeading < createAttemptA; skipLeading++) {
                ctoFetchAll.setFirstResult(skipLeading);
                persons = entityDao.query(ZooKeeperImpl.class, ctoFetchAll);

                Assert.assertEquals(persons.fetchedCount(), createAttemptA - skipLeading);
                Assert.assertEquals(persons.getTotalCount(), Long.valueOf(created));
                Assert.assertEquals(persons.getStartIndex(), skipLeading);
            }
        }
        methodTimeCounter.noticeOnExit();
    }
}
