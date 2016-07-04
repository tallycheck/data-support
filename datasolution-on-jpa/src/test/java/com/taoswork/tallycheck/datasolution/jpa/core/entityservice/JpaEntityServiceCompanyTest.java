package com.taoswork.tallycheck.datasolution.jpa.core.entityservice;

import com.taoswork.tallycheck.dataservice.PersistableResult;
import com.taoswork.tallycheck.datasolution.IDataSolution;
import com.taoswork.tallycheck.dataservice.query.CriteriaQueryResult;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.dataservice.query.PropertyFilterCriteria;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.datasolution.jpa.servicemockup.TallyMockupDataSolution;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.general.solution.time.MethodTimeCounter;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.ICompany;
import com.taoswork.tallycheck.testmaterial.jpa.domain.business.impl.CompanyImpl;
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
public class JpaEntityServiceCompanyTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JpaEntityServiceCompanyTest.class);

    private IDataSolution dataService = null;

    @Before
    public void setup() {
        dataService = new TallyMockupDataSolution();
    }

    @After
    public void teardown() {
        dataService = null;
    }

    @Test
    public void testDynamicEntityService() {
        MethodTimeCounter methodTimeCounter = new MethodTimeCounter(LOGGER);
        for (int i = 0; i < 10; ++i) {
            try {
                IEntityService entityService = dataService.getService(IEntityService.COMPONENT_NAME);
                ICompany company = new CompanyImpl();
                {
                    company.setAsset(Long.valueOf(i));
                    List<String> privateProducts = new ArrayList<String>();
                    for (int c = 0; c < i; ++c) {
                        privateProducts.add("private " + c);
                    }
                    company.setPrivateProducts(privateProducts);
                    PersistableResult<ICompany> result = entityService.create(company);
                }

                PersistableResult<ICompany> readCompanyR = entityService.read(ICompany.class, company.getId());
                ICompany companyByRead = readCompanyR.getValue();
                {
                    Assert.assertEquals(company.getId(), companyByRead.getId());
                    List<String> privateProducts = companyByRead.getPrivateProducts();
                    int productCount = 0;
                    if (privateProducts != null) {
                        productCount = privateProducts.size();
                    }
                    Assert.assertEquals("JpaEntityService.read should be able to got entity detail", productCount, i);
                }

                {
                    CriteriaTransferObject cto = new CriteriaTransferObject();
                    cto.addFilterCriteria(new PropertyFilterCriteria("asset", "" + company.getAsset()));
                    CriteriaQueryResult<ICompany> companys = entityService.query(ICompany.class, cto);
                    Assert.assertEquals(Long.valueOf(1), companys.getTotalCount());
                    ICompany theComp = companys.getEntityCollection().get(0);
                    Assert.assertNotNull(theComp);

                    List<String> privateProducts = theComp.getPrivateProducts();
                    int productCount = 0;
                    if (privateProducts != null) {
                        productCount = privateProducts.size();
                    }
                    Assert.assertEquals("JpaEntityService.query should not contains collection", productCount, 0);
                }

                {
                    CriteriaQueryResult<ICompany> companys = entityService.query(ICompany.class, null);
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