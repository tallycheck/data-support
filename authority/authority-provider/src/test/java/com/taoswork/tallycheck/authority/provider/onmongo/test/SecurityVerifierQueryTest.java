package com.taoswork.tallycheck.authority.provider.onmongo.test;

import com.taoswork.tallycheck.authority.client.IAuthorityVerifier;
import com.taoswork.tallycheck.authority.client.KAccessibleScopeWithProtection;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.KAccessibleScope;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.provider.onmongo.client.MollyOnMongoClient;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.*;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierQueryTest extends VerifierTestSupport {

    private final ProtectionScope PS = new ProtectionScope(mockuper.PROTECTION_SPEC, mockuper.PROTECTION_REGION);

    @BeforeClass
    public static void beforeClass() throws ServiceException {
        setupDatabaseData();
        makeDatabaseTestData();
    }

    @AfterClass
    public static void afterClass() {
        teardownDatabaseData();
    }

    @Test
    public void testQueryWithoutCases() throws ServiceException {
        IAuthorityVerifier client = new MollyOnMongoClient(authorityProvider);

        Class<CCFile> resourceClz = CCFile.class;
        XFileRepo<CCFile> XFileRepo = new XFileRepo<CCFile>(entityService.getDatastore(), resourceClz);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = resourceClz.getName();

        for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;
            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        for (String user : new String[]{User_G____, User_GAB__, User_GABCD}) {
            {
                KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
                KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
                IKProtection protection = accessibleScopeWithProtection.protection;
                List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
                queryLikeChecker(results)
                        .check(file_G_____, true)
                        .check(file__A____, true)
                        .check(file____C__, true)
                        .check(file__AB___, true)
                        .check(file__A_C__, true)
                        .check(file____CD_, true)
                        .check(file__ABC__, true)
                        .check(file__A_CD_, true)
                        .check(file__ABCD_, true)
                        .check(file______E, true)
                        .check(file__ABCDE, true)
                        .ensureFullyChecked();
            }
        }
    }

    @Test
    public void testQuery_MasterControl_FitAll() throws ServiceException {
        IAuthorityVerifier client = new MollyOnMongoClient(authorityProvider);
        Class<CM1File> resourceClz = CM1File.class;
        XFileRepo<CM1File> XFileRepo = new XFileRepo<CM1File>(entityService.getDatastore(), resourceClz);

        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = resourceClz.getName();

        for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_G____;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_GAB__;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_GABCD;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<CM1File> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testQuery_MasterControl_FitAny() throws ServiceException {
        IAuthorityVerifier client = new MollyOnMongoClient(authorityProvider);
        Class<CM0File> resourceClz = CM0File.class;
        XFileRepo<CM0File> XFileRepo = new XFileRepo<CM0File>(entityService.getDatastore(), resourceClz);

        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = resourceClz.getName();

        for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_G____;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_GAB__;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, false)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = User_GABCD;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testQuery_SelfControl_FitAll() throws ServiceException {
        IAuthorityVerifier client = new MollyOnMongoClient(authorityProvider);
        Class<CS1File> resourceClz = CS1File.class;
        XFileRepo<CS1File> XFileRepo = new XFileRepo<CS1File>(entityService.getDatastore(), resourceClz);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = resourceClz.getName();

        {
            String user = User_N____;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User__AB__;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User__ABCD;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, false)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = User_G____;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_GAB__;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_GABCD;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testQuery_SelfControl_FitAny() throws ServiceException {
        IAuthorityVerifier client = new MollyOnMongoClient(authorityProvider);
        Class<CS0File> resourceClz = CS0File.class;
        XFileRepo<CS0File> XFileRepo = new XFileRepo<CS0File>(entityService.getDatastore(), resourceClz);
        makeResourceInstanceMembers(resourceClz);
        String resourceEntry = resourceClz.getName();

        {
            String user = User_N____;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, false)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User__AB__;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, false)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, false)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = User__ABCD;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, false)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, false)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = User_G____;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, false)
                    .check(file____C__, false)
                    .check(file__AB___, false)
                    .check(file__A_C__, false)
                    .check(file____CD_, false)
                    .check(file__ABC__, false)
                    .check(file__A_CD_, false)
                    .check(file__ABCD_, false)
                    .check(file______E, true)
                    .check(file__ABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = User_GAB__;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, false)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, false)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = User_GABCD;
            KAccessibleScopeWithProtection accessibleScopeWithProtection = client.calcAccessibleScope(PS, user, resourceEntry, mockuper.normalAccess);
            KAccessibleScope accessibleScope = accessibleScopeWithProtection.accessibleScope;
            IKProtection protection = accessibleScopeWithProtection.protection;

            List<? extends XFile> results = XFileRepo.query(accessibleScope, protection);
            queryLikeChecker(results)
                    .check(file_G_____, true)
                    .check(file__A____, true)
                    .check(file____C__, true)
                    .check(file__AB___, true)
                    .check(file__A_C__, true)
                    .check(file____CD_, true)
                    .check(file__ABC__, true)
                    .check(file__A_CD_, true)
                    .check(file__ABCD_, true)
                    .check(file______E, true)
                    .check(file__ABCDE, true)
                    .ensureFullyChecked();
        }
    }

    private QueryLikeChecker queryLikeChecker(List<?> results) {
        return new QueryLikeChecker(results);
    }

    class QueryLikeChecker {
        private List<?> results;
        private int checked = 0;

        public QueryLikeChecker(List<?> results) {
            this.results = results;
        }

        public QueryLikeChecker check(Serializable instance, boolean exist) {
            XFile doc = (XFile) instance;
            if (exist) {
                Assert.assertTrue(results.contains(doc));
            } else {
                Assert.assertFalse(results.contains(doc));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(docCount, checked);
        }
    }
}
