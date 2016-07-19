package com.taoswork.tallycheck.datasolution.mongo;

import com.taoswork.tallycheck.authority.provider.AllPassAuthorityProvider;
import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.datasolution.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.servicemockup.TallyMockupMongoDataSolution;
import com.taoswork.tallycheck.datasolution.mongo.servicemockup.datasource.TallyMockupMongoDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.service.EntityMetaAccess;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.testmaterial.mongo.domain.nature.Citizen;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public class MongoDataSolutionTest {
    private IDataSolution dataSolution = null;
    private SecurityAccessor accessor = new SecurityAccessor();
    private Operator operator = new Operator();

    @Before
    public void setup() {
        dataSolution = new TallyMockupMongoDataSolution();
        dataSolution.setAuthorityProvider(new AllPassAuthorityProvider());
    }

    @After
    public void teardown() {
        TallyMockupMongoDatasourceConfiguration.DatasourceDefinition mdbDef = dataSolution.getService(IDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION);
        mdbDef.dropDatabase();
        dataSolution = null;
    }

    @Test
    public void testEntityEntries() {
        IEntityService entityService = dataSolution.getService(IEntityService.COMPONENT_NAME);
        Assert.assertNotNull(entityService);

        {
            Class entityType = Citizen.class;
            String citizenResName = dataSolution.getEntityResourceName(entityType.getName());
            String citizenTypeName = dataSolution.getEntityTypeName(citizenResName);

            Assert.assertEquals(citizenResName, entityType.getSimpleName().toLowerCase());
            Assert.assertEquals(citizenTypeName, entityType.getName());

            Assert.assertNotNull(dataSolution.getEntityType(citizenTypeName));
        }

        EntityMetaAccess entityMetaAccess = dataSolution.getService(EntityMetaAccess.COMPONENT_NAME);
        Assert.assertNotNull(entityMetaAccess);

        try {
            {
                Citizen c = new Citizen();
                c.setFirstName("First");
                c.setLastName("Last");

                PersistableResult<Citizen> result = entityService.create(operator, accessor, c);

            }

        } catch (ServiceException e) {
            Assert.fail(e.getMessage());
        } finally {
            //dataSolution.
        }

        //entityService.create(ctzClz, c);

     //   entityService.create(ctzClz, c);

    }
}
