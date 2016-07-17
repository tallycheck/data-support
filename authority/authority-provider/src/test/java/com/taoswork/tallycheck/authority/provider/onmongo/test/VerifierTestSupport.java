package com.taoswork.tallycheck.authority.provider.onmongo.test;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.domain.ResourceAccess;
import com.taoswork.tallycheck.authority.provider.AllPassAuthorityProvider;
import com.taoswork.tallycheck.authority.provider.AuthorityProviderImpl;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.authority.provider.onmongo.client.service.AuthSolutionDataSolution;
import com.taoswork.tallycheck.authority.provider.onmongo.client.service.datasource.AuthSolutionDatasourceConfiguration;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TGroupAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TUserAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.*;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.datasolution.config.IDatasourceConfiguration;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallycheck.datasolution.service.IEntityService;

/**
 * Created by Gao Yuan on 2015/6/4.
 */
public class VerifierTestSupport {
    private static ResourceAccess READ_ACCESS = ResourceAccess.createByAccess(Access.Read);
    private static ResourceAccess OPEN_ACCESS = ResourceAccess.createByAccess(new Access(Access.NONE, 1));

    private  static AuthSolutionDataSolution dataSolution;

    protected static IAuthorityProvider authorityProvider;
    //    protected static PermissionEngine permissionEngine;
    protected static PermissionMockuper mockuper;
    protected static MongoEntityService entityService;
    public static final Access NORMAL_ACCESS = Access.Read;


    public static final String User_N____ = "User_N____";
    public static final String User__AB__ = "User__AB__";
    public static final String User__ABCD = "User__ABCD";
    public static final String User_G____ = "User_G____";
    public static final String User_GAB__ = "User_GAB__";
    public static final String User_GABCD = "User_GABCD";

    public static final String Group_N____ = "Group_N____";
    public static final String Group__AB__ = "Group__AB__";
    public static final String Group_G____ = "Group_G____";

//    protected IKAuthority user_N____ ;
//    protected IKAuthority user__AB__ ;
//    protected IKAuthority user__ABCD ;
//    protected IKAuthority user_G____ ;
//    protected IKAuthority user_GAB__ ;
//    protected IKAuthority user_GABCD ;

    public static final String FILE_G_____ = "FILE_G_____";
    public static final String FILE__A____ = "FILE__A____";
    public static final String FILE____C__ = "FILE____C__";
    public static final String FILE__AB___ = "FILE__AB___";
    public static final String FILE__A_C__ = "FILE__A_C__";
    public static final String FILE____CD_ = "FILE____CD_";
    public static final String FILE__ABC__ = "FILE__ABC__";
    public static final String FILE__A_CD_ = "FILE__A_CD_";
    public static final String FILE__ABCD_ = "FILE__ABCD_";
    public static final String FILE______E = "FILE______E";
    public static final String FILE__ABCDE = "FILE__ABCDE";

    protected XFile file_G_____ = null;
    protected XFile file__A____ = null;
    protected XFile file____C__ = null;
    protected XFile file__AB___ = null;
    protected XFile file__A_C__ = null;
    protected XFile file____CD_ = null;
    protected XFile file__ABC__ = null;
    protected XFile file__A_CD_ = null;
    protected XFile file__ABCD_ = null;
    protected XFile file______E = null;
    protected XFile file__ABCDE = null;
    public final int docCount = 11;

    public final static SecurityAccessor securityAccessor = new SecurityAccessor(new ProtectionScope(PermissionMockuper.PROTECTION_SPEC, PermissionMockuper.PROTECTION_REGION),"1");

    protected static void setupDatabaseData() {
        dataSolution = new AuthSolutionDataSolution();
        dataSolution.setAuthorityProvider(new AllPassAuthorityProvider());

        entityService = dataSolution.getService(IEntityService.COMPONENT_NAME);
        authorityProvider = new AuthorityProviderImpl(entityService, TUserAuthority.class, TGroupAuthority.class);
        mockuper = new PermissionMockuper(dataSolution, NORMAL_ACCESS);
    }

    protected static void teardownDatabaseData() {
        authorityProvider = null;
        AuthSolutionDatasourceConfiguration.DatasourceDefinition mdbDef = dataSolution.getService(IDatasourceConfiguration.DATA_SOURCE_PATH_DEFINITION);
        mdbDef.dropDatabase();
        dataSolution = null;
    }
//
//    @Before
//    public void setup() {
//        setupDatabaseData();
//    }
//
//    @After
//    public void teardown() {
//        teardownDatabaseData();
//    }
//
//    @Test
//    public void test() {
//        String tenant = "Function:Test";
//        makeDatabaseTestData(tenant);
//
//    }

    protected static void makeDatabaseTestData() throws ServiceException {
        String tenant = mockuper.PROTECTION_REGION;
        mockuper.makeProtectionSpec();
        mockuper.makeSecuredResource(tenant, CM1File.class, true, ProtectionMode.FitAll, true);
        mockuper.makeSecuredResource(tenant, CS1File.class, false, ProtectionMode.FitAll, true);
        mockuper.makeSecuredResource(tenant, CM0File.class, true, ProtectionMode.FitAny, true);
        mockuper.makeSecuredResource(tenant, CS0File.class, false, ProtectionMode.FitAny, true);
        mockuper.makeSecuredResource(tenant, CCFile.class, false, ProtectionMode.FitAny, false);

        for (Class resource : new Class[]{CM1File.class, CS0File.class, CM0File.class, CS1File.class, CCFile.class}) {
            mockuper.makePerson(tenant, User_N____, resource, false, false, false, false, false);
            mockuper.makePerson(tenant, User__AB__, resource, false, true, true, false, false);
            mockuper.makePerson(tenant, User__ABCD, resource, false, true, true, true, true);
            mockuper.makePerson(tenant, User_G____, resource, true, false, false, false, false);
            mockuper.makePerson(tenant, User_GAB__, resource, true, true, true, false, false);
            mockuper.makePerson(tenant, User_GABCD, resource, true, true, true, true, true);

            mockuper.makeGroup(tenant, Group_N____, resource, false, false, false, false, false);
            mockuper.makeGroup(tenant, Group__AB__, resource, false, true, true, false, false);
            mockuper.makeGroup(tenant, Group_G____, resource, true, false, false, false, false);

            mockuper.makeInstanceWithTag(FILE_G_____, resource, false, false, false, false, false);
            mockuper.makeInstanceWithTag(FILE__A____, resource, true, false, false, false, false);
            mockuper.makeInstanceWithTag(FILE____C__, resource, false, false, true, false, false);
            mockuper.makeInstanceWithTag(FILE__AB___, resource, true, true, false, false, false);
            mockuper.makeInstanceWithTag(FILE__A_C__, resource, true, false, true, false, false);
            mockuper.makeInstanceWithTag(FILE____CD_, resource, false, false, true, true, false);
            mockuper.makeInstanceWithTag(FILE__ABC__, resource, true, true, true, false, false);
            mockuper.makeInstanceWithTag(FILE__A_CD_, resource, true, false, true, true, false);
            mockuper.makeInstanceWithTag(FILE__ABCD_, resource, true, true, true, true, false);
            mockuper.makeInstanceWithTag(FILE______E, resource, false, false, false, false, true);
            mockuper.makeInstanceWithTag(FILE__ABCDE, resource, true, true, true, true, true);
        }
    }

    protected void makeResourceInstanceMembers(Class<? extends XFile> resource)throws ServiceException{
        file_G_____ = mockuper.fetchInstance(resource, FILE_G_____);
        file__A____ = mockuper.fetchInstance(resource, FILE__A____);
        file____C__ = mockuper.fetchInstance(resource, FILE____C__);
        file__AB___ = mockuper.fetchInstance(resource, FILE__AB___);
        file__A_C__ = mockuper.fetchInstance(resource, FILE__A_C__);
        file____CD_ = mockuper.fetchInstance(resource, FILE____CD_);
        file__ABC__ = mockuper.fetchInstance(resource, FILE__ABC__);
        file__A_CD_ = mockuper.fetchInstance(resource, FILE__A_CD_);
        file__ABCD_ = mockuper.fetchInstance(resource, FILE__ABCD_);
        file______E = mockuper.fetchInstance(resource, FILE______E);
        file__ABCDE = mockuper.fetchInstance(resource, FILE__ABCDE);
    }

//    protected void makeAuthoritiesUserMembers(String tenant){
//        user_N____ = permissionEngine.getAuthority(mockuper.PROTECTION_SPEC, tenant, User_N____);
//        user__AB__ = permissionEngine.getAuthority(mockuper.PROTECTION_SPEC, tenant, User__AB__);
//        user__ABCD = permissionEngine.getAuthority(mockuper.PROTECTION_SPEC, tenant, User__ABCD);
//        user_G____ = permissionEngine.getAuthority(mockuper.PROTECTION_SPEC, tenant, User_G____);
//        user_GAB__ = permissionEngine.getAuthority(mockuper.PROTECTION_SPEC, tenant, User_GAB__);
//        user_GABCD = permissionEngine.getAuthority(mockuper.PROTECTION_SPEC, tenant, User_GABCD);
//    }
}
