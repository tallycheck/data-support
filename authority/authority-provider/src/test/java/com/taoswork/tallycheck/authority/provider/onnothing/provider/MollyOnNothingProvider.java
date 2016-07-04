package com.taoswork.tallycheck.authority.provider.onnothing.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.atom.utility.ResourceUtility;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermission;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermissionCase;
import com.taoswork.tallycheck.authority.provider.onnothing.Mockuper;
import com.taoswork.tallycheck.authority.provider.BaseAuthorityProvider;
import com.taoswork.tallycheck.authority.provider.ResProtection;
import com.taoswork.tallycheck.authority.provider.ResProtectionCase;
import com.taoswork.tallycheck.authority.provider.permission.IKAuthority;
import com.taoswork.tallycheck.authority.provider.permission.authorities.SimpleKAuthority;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionMapping;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class MollyOnNothingProvider extends BaseAuthorityProvider {

    //Use a fixed access to make easy
    public final DocRepo docRepo = new DocRepo();

    public final Access normalAccess;
    public final ResProtectionCase docCaseA;
    public final ResProtectionCase docCaseB;
    public final ResProtectionCase docCaseC;
    public final ResProtectionCase docCaseD;

//    private final IKPermissionCase accessA;
//    private final IKPermissionCase accessB;
//    private final IKPermissionCase accessC;
//    private final IKPermissionCase accessD;

    public final static String userN = "user:N:00000";
    public final static String userG = "user:G:10000";
    public final static String userAB = "user:AB:01100";
    public final static String userGAB = "user:GAB:11100";
    public final static String userABCD = "user:ABCD:01111";
    public final static String userGABCD = "user:GABCD:11111";
    public final static String[] users = new String[]{userN, userG, userAB, userGAB, userABCD, userGABCD};



    private final Map<String, ResProtection> resProtectionMap = new HashMap<String, ResProtection>();

    private final Map<String, IKAuthority> authorityMap = new HashMap<String, IKAuthority>();
    private IKProtectionMapping mapping;

    public MollyOnNothingProvider(Access normalAccess) {
        this.normalAccess = normalAccess;

        docCaseA = Mockuper.protectionCase(Mockuper.TAGA);
        docCaseB = Mockuper.protectionCase(Mockuper.TAGB);
        docCaseC = Mockuper.protectionCase(Mockuper.TAGC);
        docCaseD = Mockuper.protectionCase(Mockuper.TAGD);

        for (String user : users) {
            SimpleKAuthority simpleKAuthority = new SimpleKAuthority();
            authorityMap.put(user, simpleKAuthority);
        }
    }

    private void appendPermissionsForUsers(String res){
        for (String user : users) {
            appendPermissionsForUser(res, user);
        }
    }

    private void appendPermissionsForUser(String res, String user) {
        String aaaaa = user.split(":")[2];
        boolean g = ('1' == aaaaa.charAt(0));
        boolean a = ('1' == aaaaa.charAt(1));
        boolean b = ('1' == aaaaa.charAt(2));
        boolean c = ('1' == aaaaa.charAt(3));
        boolean d = ('1' == aaaaa.charAt(4));

        SimpleKAuthority simpleKAuthority = (SimpleKAuthority) authorityMap.get(user);
        IKPermission permission = permissionWith(res, g, a, b, c, d);
        simpleKAuthority.addPermission(permission);
    }

    protected IKAuthority buildAuthority(String user){
        SimpleKAuthority simpleKAuthority = new SimpleKAuthority();
        authorityMap.put(user, simpleKAuthority);
        for(String res : this.resProtectionMap.keySet()){
            appendPermissionsForUser(res, user);
        }
        return simpleKAuthority;
    }

    public DocRepo getDocRepo() {
        return docRepo;
    }

    @Override
    protected ResProtection doGetProtection(ProtectionScope scope, String resourceTypeName) {
        return resProtectionMap.get(resourceTypeName);
    }

    @Override
    protected IKPermission doGetPermission(ProtectionScope scope, String resourceTypeName, String userId) {
        IKAuthority authority = authorityMap.get(userId);
        if(authority == null)
            return null;
        return authority.getPermission(resourceTypeName);
    }

//    public IKProtectionCenter resourceProtectionCenter(IKProtectionMapping mapping, boolean masterControlled,
//                                                       ProtectionMode protectionMode) {
//        CrudeKProtectionCenter securedResourceManager = new CrudeKProtectionCenter(mapping);
//        IKProtection resourceProtection = new KProtection(resource);
//        resourceProtection.setMasterControlled(masterControlled);
//        resourceProtection.setProtectionMode(protectionMode);
//        resourceProtection.addCases(docCaseA, docCaseB, docCaseC, docCaseD);
//
//        securedResourceManager.registerProtection(resourceProtection);
//        return securedResourceManager;
//    }

    public void registerProtectionMapping(IKProtectionMapping mapping) {
        this.mapping = mapping;
    }

    @Override
    protected IKProtectionMapping getProtectionMapping(ProtectionScope scope) {
        return this.mapping;
    }

    public void registerProtection(String resource, boolean masterControlled,
                                   ProtectionMode protectionMode) {
        String unifiedResource = ResourceUtility.unifiedResourceName(resource);
        ResProtection resProtection = new ResProtection();
        resProtection.resource = unifiedResource;
        resProtection.masterControlled = masterControlled;
        resProtection.protectionMode = protectionMode;
        resProtection.addCases(docCaseA, docCaseB, docCaseC, docCaseD);

        resProtectionMap.put(unifiedResource, resProtection);

        appendPermissionsForUsers(resource);
        TypedDocRepo typedDocRepo = new TypedDocRepo(unifiedResource);
        typedDocRepo.pushAllInto(this.docRepo);
    }

    protected KPermission permissionWith(String resource, boolean g, boolean a, boolean b, boolean c, boolean d) {
        resource = ResourceUtility.unifiedResourceName(resource);
        KPermission entityPermission = new KPermission(resource);
        KPermissionCase accessA = Mockuper.permissionCase(docCaseA, normalAccess);
        KPermissionCase accessB = Mockuper.permissionCase(docCaseB, normalAccess);
        KPermissionCase accessC = Mockuper.permissionCase(docCaseC, normalAccess);
        KPermissionCase accessD = Mockuper.permissionCase(docCaseD, normalAccess);

        entityPermission.addCases(
                a ? accessA : null,
                b ? accessB : null,
                c ? accessC : null,
                d ? accessD : null);

        entityPermission.setMasterAccess(g ? normalAccess : Access.None);

        return entityPermission;
    }

    public IKAuthority getAuthority(String user){
        IKAuthority authority =    authorityMap.get(user);
        if(authority == null){
            authority = buildAuthority(user);
        }
        return authority;
    }

    public MollyOnNothingProvider setAuthority(String user, IKAuthority authority){
        authorityMap.put(user,authority);
        return this;
    }

}
