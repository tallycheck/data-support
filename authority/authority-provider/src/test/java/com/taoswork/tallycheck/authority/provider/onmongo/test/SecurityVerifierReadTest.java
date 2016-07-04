package com.taoswork.tallycheck.authority.provider.onmongo.test;

import com.taoswork.tallycheck.authority.client.AccessClient;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.provider.onmongo.client.MollyOnMongoClient;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TGroupAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TUserAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.*;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/26.
 */
public class SecurityVerifierReadTest extends VerifierTestSupport {
    private static final String TENANT = "test-tenant";
    private final ProtectionScope PS = new ProtectionScope(mockuper.PROTECTION_SPACE, TENANT);

    @BeforeClass
    public static void beforeClass() {
        setupDatabaseData();
        makeDatabaseTestData(TENANT);
        System.out.print("xx");
    }

    @AfterClass
    public static void afterClass() {
        teardownDatabaseData();
    }

    //
    //    @Test
    //    public void testPermissionForType() {
    //        IKProtectionMapping protectionMapping = new KProtectionMapping();
    //        IKProtectionCenter resourceManager = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
    //        IKAccessVerifier accessVerifier = new KAccessVerifier(resourceManager, protectionMapping);
    //
    //        for (String user : new String[]{User__AB__, User_G____, User_GAB__}) {
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        {
    //            String user = User_N____;
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //    }
    //
    //    @Test
    //    public void testPermissionForType_ByAlias() {
    //        KProtectionMapping protectionMapping = new KProtectionMapping();
    //        IKProtectionCenter resourceManager = mocker.resourceProtectionCenter(protectionMapping, true, ProtectionMode.FitAll);
    //        IKAccessVerifier accessVerifier = new KAccessVerifier(resourceManager, protectionMapping);
    //
    //        for (String user : new String[]{User__AB__, User_G____, User_GAB__}) {
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        {
    //            String user = User_N____;
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        protectionMapping.registerAlias(resourceEntry, TypesEnums.Image)
    //                .registerAlias(resourceEntry, TypesEnums.File)
    //                .registerAlias(resourceEntry, TypesEnums.Menu);
    //        for (String user : new String[]{User__AB__, User_G____, User_GAB__}) {
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        {
    //            String user = User_N____;
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //    }
    //
    @Test
    public void testReadWithoutCases() {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CCFile.class);

        for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
            accessChecker(client, user)
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
            accessChecker(client, user)
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
    public void testReadWithCases_MasterControl_FitAll() {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CM1File.class);

        for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
    public void testReadWithCases_MasterControl_FitAll_WithGroups() throws ServiceException {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CM1File.class);

        TGroupAuthority d_group_N____ = mockuper.getGroupAuthority(TENANT, Group_N____);
        TGroupAuthority d_group__AB__ = mockuper.getGroupAuthority(TENANT, Group__AB__);
        TGroupAuthority d_group_G____ = mockuper.getGroupAuthority(TENANT, Group_G____);

        TUserAuthority d_user_N____ = mockuper.getUserAuthority(TENANT, User_N____);
        TUserAuthority d_user__AB__ = mockuper.getUserAuthority(TENANT, User__AB__);
        TUserAuthority d_user__ABCD = mockuper.getUserAuthority(TENANT, User__ABCD);
        TUserAuthority d_user_G____ = mockuper.getUserAuthority(TENANT, User_G____);
        TUserAuthority d_user_GAB__ = mockuper.getUserAuthority(TENANT, User_GAB__);
        TUserAuthority d_user_GABCD = mockuper.getUserAuthority(TENANT, User_GABCD);

        TUserAuthority[] d_users = new TUserAuthority[]{d_user_N____, d_user__AB__, d_user__ABCD, d_user_G____, d_user_GAB__, d_user_GABCD};
        {
            for (TUserAuthority du : d_users) {
                List<TGroupAuthority> ga = new ArrayList<TGroupAuthority>();
                ga.add(d_group_N____);
                du.setGroups(ga);
                entityService.update(du);
            }

            for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
                accessChecker(client, user)
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
                accessChecker(client, user)
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
                accessChecker(client, user)
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
                accessChecker(client, user)
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
        {
            for (TUserAuthority du : d_users) {
                List<TGroupAuthority> ga = new ArrayList<TGroupAuthority>();
                ga.add(d_group__AB__);
                du.setGroups(ga);
                entityService.update(du);
            }

            for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
                accessChecker(client, user)
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
            for (String user : new String[]{User_G____, User_GAB__}) {
                accessChecker(client, user)
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
                accessChecker(client, user)
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
        {
            for (TUserAuthority du : d_users) {
                List<TGroupAuthority> ga = new ArrayList<TGroupAuthority>();
                ga.add(d_group_G____);
                du.setGroups(ga);
                entityService.update(du);
            }

            for (String user : new String[]{User_N____, User_G____}) {
                accessChecker(client, user)
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
            for (String user : new String[]{User__AB__, User_GAB__}) {
                accessChecker(client, user)
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
            for (String user : new String[]{User__ABCD, User_GABCD}) {
                accessChecker(client, user)
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
        for (TUserAuthority du : d_users) {
            List<TGroupAuthority> ga = new ArrayList<TGroupAuthority>();
            du.setGroups(ga);
            entityService.update(du);
        }
    }

    @Test
    public void testReadWithCases_MasterControl_FitAny() {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CM0File.class);

        for (String user : new String[]{User_N____, User__AB__, User__ABCD}) {
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
    public void testReadWithCases_SelfControl_FitAll() {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CS1File.class);

        {
            String user = User_N____;
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
    public void testReadWithCases_SelfControl_FitAny() {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CS0File.class);

        {
            String user = User_N____;
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
            accessChecker(client, user)
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
    public void testReadWithCases_MasterControl_FitAll_FileChain() {
        AccessClient client = new MollyOnMongoClient(authorityProvider);
        makeResourceInstanceMembers(CM1File.class);
        String resourceEntry = CM1File.class.getName();

        {
            String user = User_G____;
            accessChecker(client, user)
                    .multiCheck(true, resourceEntry, file_G_____, file_G_____)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____)
                    .multiCheck(false, resourceEntry, file__A____, file__AB___)
                    .multiCheck(false, resourceEntry, file_G_____, file__A_C__)
                    .multiCheck(true, resourceEntry, file_G_____, file______E)
                    .multiCheck(false, resourceEntry, file_G_____, file__ABCD_);
        }
        {
            String user = User_GAB__;
            accessChecker(client, user)
                    .multiCheck(true, resourceEntry, file_G_____, file__A____, file__AB___, file______E)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file____C__)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file__A_C__)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file____CD_)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file__ABC__)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file__A_CD_)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file__ABCD_)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____, file__AB___, file______E, file__ABCDE);
        }
        {
            String user = User_GABCD;

            accessChecker(client, user)
                    .multiCheck(true, resourceEntry, file_G_____, file__A____, file____C__, file__AB___, file__A_C__, file____CD_, file__ABC__, file__A_CD_, file__ABCD_, file______E, file__ABCDE);
        }
    }

    private AccessChecker accessChecker(AccessClient client, String user) {
        return new AccessChecker(client, user);
    }

    class AccessChecker {
        private final AccessClient client;
        private final String user;
        private int checked = 0;

        public AccessChecker(AccessClient client, String user) {
            this.client = client;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, String resourceEntry, Serializable... instances) {
            boolean canAccess = client.canAccess(PS, resourceEntry, NORMAL_ACCESS, user, instances);

            if (allowed) {
                Assert.assertTrue(canAccess);
            } else {
                Assert.assertFalse(canAccess);
            }
            checked++;
            return this;
        }

        public AccessChecker check(Serializable instance, boolean allowed) {
            String resourceEntry = instance.getClass().getName();
            return multiCheck(allowed, resourceEntry, instance);
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(docCount, checked);
        }
    }

}
