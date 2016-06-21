package com.taoswork.tallycheck.authority.solution.engine.verifier;

import com.taoswork.tallycheck.authority.core.permission.IKAuthority;
import com.taoswork.tallycheck.authority.core.verifier.IKAccessVerifier;
import com.taoswork.tallycheck.authority.solution.domain.resource.Protection;
import com.taoswork.tallycheck.authority.solution.mockup.domain.auth.TGroupAuthority;
import com.taoswork.tallycheck.authority.solution.mockup.domain.auth.TUserAuthority;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.authority.solution.mockup.domain.resource.*;
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
    //        for (IKAuthority user : new IKAuthority[]{user__AB__, user_G____, user_GAB__}) {
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        {
    //            IKAuthority user = user_N____;
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
    //        for (IKAuthority user : new IKAuthority[]{user__AB__, user_G____, user_GAB__}) {
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        {
    //            IKAuthority user = user_N____;
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        protectionMapping.registerAlias(resourceEntry, TypesEnums.Image)
    //                .registerAlias(resourceEntry, TypesEnums.File)
    //                .registerAlias(resourceEntry, TypesEnums.Menu);
    //        for (IKAuthority user : new IKAuthority[]{user__AB__, user_G____, user_GAB__}) {
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertTrue(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //
    //        {
    //            IKAuthority user = user_N____;
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, resourceEntry));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Image));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.File));
    //            Assert.assertFalse(accessVerifier.canAccess(user, mocker.normalAccess, TypesEnums.Menu));
    //        }
    //    }
    //
    @Test
    public void testReadWithoutCases() {
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(CCFile.class);

        for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
            accessChecker(accessVerifier, user)
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
        for (IKAuthority user : new IKAuthority[]{user_G____, user_GAB__, user_GABCD}) {
            accessChecker(accessVerifier, user)
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
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(CM1File.class);

        for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_G____;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GAB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GABCD;
            accessChecker(accessVerifier, user)
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
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeResourceInstanceMembers(CM1File.class);
//        public static final String Group_N____ = "Group_N____";
//        public static final String Group__AB__ = "Group__AB__";
//        public static final String Group_G____ = "Group_G____";
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

            makeAuthoritiesUserMembers(TENANT);

            for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
                accessChecker(accessVerifier, user)
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
                IKAuthority user = user_G____;
                accessChecker(accessVerifier, user)
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
                IKAuthority user = user_GAB__;
                accessChecker(accessVerifier, user)
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
                IKAuthority user = user_GABCD;
                accessChecker(accessVerifier, user)
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

            makeAuthoritiesUserMembers(TENANT);

            for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
                accessChecker(accessVerifier, user)
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
            for (IKAuthority user : new IKAuthority[]{user_G____, user_GAB__}) {
                accessChecker(accessVerifier, user)
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
                IKAuthority user = user_GABCD;
                accessChecker(accessVerifier, user)
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

            makeAuthoritiesUserMembers(TENANT);

            for (IKAuthority user : new IKAuthority[]{user_N____, user_G____}) {
                accessChecker(accessVerifier, user)
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
            for (IKAuthority user : new IKAuthority[]{user__AB__, user_GAB__}) {
                accessChecker(accessVerifier, user)
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
            for (IKAuthority user : new IKAuthority[]{user__ABCD, user_GABCD}) {
                accessChecker(accessVerifier, user)
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
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(CM0File.class);

        for (IKAuthority user : new IKAuthority[]{user_N____, user__AB__, user__ABCD}) {
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_G____;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GAB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GABCD;
            accessChecker(accessVerifier, user)
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
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(CS1File.class);

        {
            IKAuthority user = user_N____;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user__AB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user__ABCD;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_G____;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GAB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GABCD;
            accessChecker(accessVerifier, user)
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
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(CS0File.class);

        {
            IKAuthority user = user_N____;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user__AB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user__ABCD;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_G____;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GAB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GABCD;
            accessChecker(accessVerifier, user)
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
        IKAccessVerifier accessVerifier = permissionEngine.getAccessVerifier(mockuper.PROTECTION_SPACE, TENANT);
        makeAuthoritiesUserMembers(TENANT);
        makeResourceInstanceMembers(CM1File.class);
        String resourceEntry = CM1File.class.getName();
        resourceEntry = Protection.unifiedResourceName(resourceEntry);

        {
            IKAuthority user = user_G____;
            accessChecker(accessVerifier, user)
                    .multiCheck(true, resourceEntry, file_G_____, file_G_____)
                    .multiCheck(false, resourceEntry, file_G_____, file__A____)
                    .multiCheck(false, resourceEntry, file__A____, file__AB___)
                    .multiCheck(false, resourceEntry, file_G_____, file__A_C__)
                    .multiCheck(true, resourceEntry, file_G_____, file______E)
                    .multiCheck(false, resourceEntry, file_G_____, file__ABCD_);
        }
        {
            IKAuthority user = user_GAB__;
            accessChecker(accessVerifier, user)
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
            IKAuthority user = user_GABCD;

            accessChecker(accessVerifier, user)
                    .multiCheck(true, resourceEntry, file_G_____, file__A____, file____C__, file__AB___, file__A_C__, file____CD_, file__ABC__, file__A_CD_, file__ABCD_, file______E, file__ABCDE);
        }
    }

    private AccessChecker accessChecker(IKAccessVerifier accessVerifier, IKAuthority user) {
        return new AccessChecker(accessVerifier, user);
    }

    class AccessChecker {
        private final IKAccessVerifier accessVerifier;
        private final IKAuthority user;
        private int checked = 0;

        public AccessChecker(IKAccessVerifier accessVerifier, IKAuthority user) {
            this.accessVerifier = accessVerifier;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, String resourceEntry, Serializable... instances) {
            resourceEntry = Protection.unifiedResourceName(resourceEntry);
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mockuper.normalAccess, resourceEntry, instances));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mockuper.normalAccess, resourceEntry, instances));
            }
            checked++;
            return this;
        }

        public AccessChecker check(Serializable instance, boolean allowed) {
            String resourceEntry = instance.getClass().getName();
            resourceEntry = Protection.unifiedResourceName(resourceEntry);
            if (allowed) {
                Assert.assertTrue(accessVerifier.canAccess(user, mockuper.normalAccess, resourceEntry, instance));
            } else {
                Assert.assertFalse(accessVerifier.canAccess(user, mockuper.normalAccess, resourceEntry, instance));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(docCount, checked);
        }
    }
}
