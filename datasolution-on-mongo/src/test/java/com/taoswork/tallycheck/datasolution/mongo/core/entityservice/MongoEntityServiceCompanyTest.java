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
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.general.solution.time.MethodTimeCounter;
import com.taoswork.tallycheck.testmaterial.mongo.domain.business.ICompany;
import com.taoswork.tallycheck.testmaterial.mongo.domain.business.impl.CompanyImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/6/26.
 */
public class MongoEntityServiceCompanyTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoEntityServiceCompanyTest.class);

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
        TallyMockupMongoDatasourceConfiguration.DatasourceDefinition mdbDef = dataSolution.getService(DatasourceDefinition.DATA_SOURCE_DEFINITION);
        mdbDef.dropDatabase();
        dataSolution = null;
    }

    @Test
    public void testDynamicEntityService() {
        MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER);
        for (int i = 0; i < 10; ++i) {
            try {
                IEntityService entityService = dataSolution.getService(IEntityService.COMPONENT_NAME);
                EasyEntityService easyEntityService = new EasyEntityService(dataSolution);

                ICompany company = new CompanyImpl();
                {
                    company.setAsset(Long.valueOf(i));
                    List<String> privateProducts = new ArrayList<String>();
                    for (int c = 0; c < i; ++c) {
                        privateProducts.add("private " + c);
                    }
                    company.setPrivateProducts(privateProducts);
                    PersistableResult<ICompany> result = entityService.create(operator, accessor, company);
                }

                PersistableResult<ICompany> readCompanyR = easyEntityService.read(operator, accessor, ICompany.class, company.getId());
                ICompany companyByRead = readCompanyR.getValue();
                {
                    Assert.assertEquals(company.getId(), companyByRead.getId());
                    List<String> privateProducts = companyByRead.getPrivateProducts();
                    int productCount = 0;
                    if (privateProducts != null) {
                        productCount = privateProducts.size();
                    }
                    Assert.assertEquals("EntityService.read should be able to got entity detail", productCount, i);
                }

                {
                    CriteriaTransferObject cto = new CriteriaTransferObject();
                    cto.addFilterCriteria(new PropertyFilterCriteria("asset", "" + company.getAsset()));
                    CriteriaQueryResult<ICompany> companys = easyEntityService.query(accessor, ICompany.class, cto);
                    Assert.assertEquals(Long.valueOf(1), companys.getTotalCount());
                    ICompany theComp = companys.getEntityCollection().get(0);
                    Assert.assertNotNull(theComp);

                    List<String> privateProducts = theComp.getPrivateProducts();
                    int productCount = 0;
                    if (privateProducts != null) {
                        productCount = privateProducts.size();
                    }
//                    Assert.assertEquals("EntityServiceMark.query should not contains collection", productCount, 0);
                }

                {
                    CriteriaQueryResult<ICompany> companys = easyEntityService.query(accessor, ICompany.class, null);
                    Assert.assertNotNull(companys);
                    Assert.assertEquals(i + 1, companys.fetchedCount());
                }
            } catch (ServiceException e) {
                e.printStackTrace();
                Assert.fail();
            }
        }
        methodTimeCounter.noticeOnExit();
    }
}