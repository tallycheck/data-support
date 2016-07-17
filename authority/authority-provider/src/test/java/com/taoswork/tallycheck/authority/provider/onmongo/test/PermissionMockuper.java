package com.taoswork.tallycheck.authority.provider.onmongo.test;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.atom.utility.ResourceUtility;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.domain.ProtectionSpec;
import com.taoswork.tallycheck.authority.domain.ResourceAccess;
import com.taoswork.tallycheck.authority.domain.permission.Permission;
import com.taoswork.tallycheck.authority.domain.permission.PermissionCase;
import com.taoswork.tallycheck.authority.domain.resource.DProtectionMode;
import com.taoswork.tallycheck.authority.domain.resource.Protection;
import com.taoswork.tallycheck.authority.domain.resource.ProtectionCase;
import com.taoswork.tallycheck.authority.domain.user.BaseAuthority;
import com.taoswork.tallycheck.authority.domain.user.GroupAuthority;
import com.taoswork.tallycheck.authority.domain.user.UserAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.client.service.AuthSolutionDataSolution;
import com.taoswork.tallycheck.authority.provider.onmongo.common.ClassifiedFilters;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TGroupAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.auth.TUserAuthority;
import com.taoswork.tallycheck.authority.provider.onmongo.common.domain.resource.XFile;
import com.taoswork.tallycheck.dataservice.SecurityAccessor;
import com.taoswork.tallycheck.dataservice.exception.ServiceException;
import com.taoswork.tallycheck.dataservice.query.CriteriaTransferObject;
import com.taoswork.tallycheck.datasolution.mongo.core.entityservice.MongoEntityService;
import com.taoswork.tallycheck.datasolution.service.EasyEntityService;
import com.taoswork.tallycheck.datasolution.service.IEntityService;
import com.taoswork.tallycheck.descriptor.dataio.copier.fieldcopier.CopyLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class PermissionMockuper {
    public static final String PROTECTION_SPEC = "test";
    public static final String PROTECTION_REGION = "test-region";

    public static final ProtectionScope PS = new ProtectionScope(PROTECTION_SPEC, PROTECTION_REGION);

    public static final String CASE_A_NAME = "A";
    public static final String CASE_B_NAME = "B";
    public static final String CASE_C_NAME = "C";
    public static final String CASE_D_NAME = "D";
    public static final String[] CASE_NAMES = new String[]{CASE_A_NAME, CASE_B_NAME, CASE_C_NAME, CASE_D_NAME};

    public static final String CASE_A_TAG = "a";
    public static final String CASE_B_TAG = "b";
    public static final String CASE_C_TAG = "c";
    public static final String CASE_D_TAG = "d";
    public static final String CASE_E_TAG = "e";

    public static final String CASE_A_PARA = "a";
    public static final String CASE_B_PARA = "b";
    public static final String CASE_C_PARA = "c";
    public static final String CASE_D_PARA = "d";
    public static final String[] CASE_PARAS = new String[]{CASE_A_PARA, CASE_B_PARA, CASE_C_PARA, CASE_D_PARA};

    public static final int CASE_COUNT = 4;

    private final AuthSolutionDataSolution dataService;
    private final MongoEntityService entityService;
    private final EasyEntityService easyEntityAccess;
    public final Access normalAccess;

    private static final SecurityAccessor accessor = new SecurityAccessor();

    public PermissionMockuper(AuthSolutionDataSolution dataService, Access normalAccess) {
        this.dataService = dataService;
        this.entityService = dataService.getService(IEntityService.COMPONENT_NAME);
        this.easyEntityAccess = new EasyEntityService(dataService);
        this.normalAccess = normalAccess;
    }

    public void makeProtectionSpec() {
        ProtectionSpec ps = new ProtectionSpec();
        ps.setSpecName(PROTECTION_SPEC);
        easyEntityAccess.create(accessor, ps);
    }

    public void makeSecuredResource(String region, Class resource,
                                    boolean masterControlled,
                                    ProtectionMode pm, boolean addCases) {
        Protection sr = new Protection();
        sr.setProtectionSpec(PROTECTION_SPEC);
        sr.setProtectionRegion(region);
        sr.setResource(resource.getName());
        sr.setName(resource.getSimpleName());
        sr.setProtectionMode(DProtectionMode.fromNativeType(pm));
        sr.setMasterControlled(masterControlled);

        if(addCases) {
            for (int i = 0; i < CASE_COUNT; ++i) {
                String caseName = CASE_NAMES[i];
                String casePara = CASE_PARAS[i];
                ProtectionCase _case = new ProtectionCase();
                _case.autoUuid();
                _case.setName(caseName);
                _case.setFilter(ClassifiedFilters.BY_CLASSIFY);
                _case.setFilterParameter(casePara);
                sr.addCase(_case);
            }
        }
        easyEntityAccess.create(accessor, sr);
    }

    public Protection getResource(String tanantId, Class resource) throws ServiceException {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(Protection.FN_PROTECTION_SPEC, PROTECTION_SPEC)
                .addFilterCriteria(Protection.FN_PROTECTION_REGION, tanantId)
                .addFilterCriteria(Protection.FN_RESOURCE_ENTITY, resource.getName());
        Protection sr = easyEntityAccess.queryOne(accessor, Protection.class, cto, CopyLevel.Swap);
        return sr;
    }

    public <T extends UserAuthority> T getUserAuthority(String region, String userId) throws ServiceException {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(UserAuthority.FN_PROTECTION_SPEC, PROTECTION_SPEC)
                .addFilterCriteria(UserAuthority.FN_PROTECTION_REGION, region)
                .addFilterCriteria(UserAuthority.FN_OWNER_ID, userId);
        BaseAuthority pp = easyEntityAccess.queryOne(accessor, UserAuthority.class, cto, CopyLevel.Swap);
        return (T) pp;
    }

    public <T extends GroupAuthority> T getGroupAuthority(String region, String groupId) throws ServiceException {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(UserAuthority.FN_PROTECTION_SPEC, PROTECTION_SPEC)
                .addFilterCriteria(UserAuthority.FN_PROTECTION_REGION, region)
                .addFilterCriteria(UserAuthority.FN_OWNER_ID, groupId);
        BaseAuthority pp = easyEntityAccess.queryOne(accessor, GroupAuthority.class, cto, CopyLevel.Swap);
        return (T) pp;
    }

    public void makePerson(String region, String userId, Class resource,
                           boolean g, boolean a, boolean b, boolean c, boolean d) throws ServiceException {
        UserAuthority ua = new TUserAuthority();
        makeAuthorizable(region, userId, resource, UserAuthority.class, ua, g, a, b, c, d);
    }

    public void makeGroup(String region, String userId, Class resource,
                           boolean g, boolean a, boolean b, boolean c, boolean d) throws ServiceException {
        GroupAuthority ga = new TGroupAuthority();
        ga.setName(userId);
        makeAuthorizable(region, userId, resource, GroupAuthority.class, ga, g, a, b, c, d);
    }

    public <T extends BaseAuthority> void makeAuthorizable(String region, String ownerId, Class resource,
                                                           Class<T> authorizableClz, T newInstance,
                           boolean g, boolean a, boolean b, boolean c, boolean d) throws ServiceException {
        String resourceName = resource.getName();
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria(UserAuthority.FN_PROTECTION_SPEC, PROTECTION_SPEC)
                .addFilterCriteria(UserAuthority.FN_PROTECTION_REGION, region)
                .addFilterCriteria(UserAuthority.FN_OWNER_ID, ownerId);
        BaseAuthority pp = easyEntityAccess.queryOne(accessor, authorizableClz, cto, CopyLevel.Swap);
        boolean isNew = false;
        if (pp == null) {
            pp = newInstance;
            pp.setProtectionSpec(PROTECTION_SPEC);
            pp.setProtectionRegion(region);
            pp.setOwnerId(ownerId);
            isNew = true;
        }

        Permission rp = pp.getPermission(resourceName);
        if (rp == null) {
            rp = new Permission();
            rp.setResource(resourceName);
            rp.setName(resource.getSimpleName());
            pp.addPermission(rp);
        }
        if (g)
            rp.setAccess(ResourceAccess.createByAccess(normalAccess));
        rp.clearPermissionCases();
        Protection protection = this.getResource(region, resource);
        Map<String, ProtectionCase> resourceCases = protection.getCases();
        if (resourceCases == null) {
            resourceCases = new HashMap<String, ProtectionCase>();
        }
        if (resourceCases != null) {
            for (ProtectionCase resCase : resourceCases.values()) {
                PermissionCase permissionCase = new PermissionCase();
                permissionCase.setCode(resCase.getUuid());
                String caseName = resCase.getName();
                if (CASE_A_NAME.equals(caseName) && a) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                } else if (CASE_B_NAME.equals(caseName) && b) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                } else if (CASE_C_NAME.equals(caseName) && c) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                } else if (CASE_D_NAME.equals(caseName) && d) {
                    permissionCase.setAccess(ResourceAccess.createByAccess(normalAccess));
                }
                rp.addPermissionCase(permissionCase);
            }
        }

        if (isNew) {
            easyEntityAccess.create(accessor, (T) pp);
        } else {
            easyEntityAccess.update(accessor, (T) pp);
        }
    }

    public <T extends XFile> void makeInstanceWithTag(String title, Class<T> fileType, boolean a, boolean b, boolean c, boolean d, boolean e) {
        T instance = null;
        try {
            instance = fileType.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
        instance.setTitle(title);
        if (a) instance.addClassification(CASE_A_TAG);
        if (b) instance.addClassification(CASE_B_TAG);
        if (c) instance.addClassification(CASE_C_TAG);
        if (d) instance.addClassification(CASE_D_TAG);
        if (e) instance.addClassification(CASE_E_TAG);
        easyEntityAccess.create(accessor, instance);
    }

    public <T extends XFile> T fetchInstance(Class<T> fileType, String fileTitle) throws ServiceException {
        CriteriaTransferObject cto = new CriteriaTransferObject();
        cto.addFilterCriteria("title", fileTitle);
        return easyEntityAccess.queryOne(accessor, fileType, cto, CopyLevel.Swap);
    }

}
