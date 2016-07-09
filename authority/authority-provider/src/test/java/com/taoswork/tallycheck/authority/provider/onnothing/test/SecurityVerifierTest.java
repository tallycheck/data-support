package com.taoswork.tallycheck.authority.provider.onnothing.test;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.client.IAuthorityVerifier;
import com.taoswork.tallycheck.authority.client.KAccessibleScopeWithProtection;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.provider.onnothing.client.MollyOnNothingClient;
import com.taoswork.tallycheck.authority.provider.onnothing.common.NativeDoc;
import com.taoswork.tallycheck.authority.provider.onnothing.common.TypesEnums;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.GuardedDocInstance;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.MollyOnNothingProvider;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.TypedDocRepo;
import com.taoswork.tallycheck.authority.provider.permission.IKAuthority;
import com.taoswork.tallycheck.authority.provider.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionMapping;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionMapping;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final Access NORMAL_ACCESS = Access.Read;
    private final TypedDocRepo TYPED_RES_REPO = new TypedDocRepo(resourceEntry);
    private final ProtectionScope PS = null;

    private static final String userGA = "user:GA:11000";
    private static final String userGB = "user:GB:10100";
    private static final String userGC = "user:GC:10010";
    private static final String userGD = "user:GD:10001";

    private AccessChecker accessChecker(MollyOnNothingClient client, String user) {
        return new AccessChecker(client, user);
    }

    private QueryLikeChecker queryLikeChecker(List<?> results) {
        return new QueryLikeChecker(results);
    }

    @Test
    public void testPermissionForType() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        for (String user : new String[]{MollyOnNothingProvider.userAB, MollyOnNothingProvider.userG, MollyOnNothingProvider.userGAB}) {
            Assert.assertTrue(client.canAccess(PS, user, resourceEntry, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user, TypesEnums.Image, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user, TypesEnums.File, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user, TypesEnums.Menu, NORMAL_ACCESS));
        }

        {
            String user = MollyOnNothingProvider.userN;
            Assert.assertFalse(client.canAccess(PS, user,  resourceEntry, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Image, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.File, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Menu, NORMAL_ACCESS));
        }
    }

    @Test
    public void testPermissionForType_ByAlias() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        KProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        for (String user : new String[]{MollyOnNothingProvider.userAB, MollyOnNothingProvider.userG, MollyOnNothingProvider.userGAB}) {
            Assert.assertTrue(client.canAccess(PS, user,  resourceEntry, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Image, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.File, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Menu, NORMAL_ACCESS));
        }

        {
            String user = MollyOnNothingProvider.userN;
            Assert.assertFalse(client.canAccess(PS, user,  resourceEntry, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Image, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.File, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Menu, NORMAL_ACCESS));
        }

        protectionMapping.registerAlias(resourceEntry, TypesEnums.Image)
                .registerAlias(resourceEntry, TypesEnums.File)
                .registerAlias(resourceEntry, TypesEnums.Menu);
        for (String user : new String[]{MollyOnNothingProvider.userAB, MollyOnNothingProvider.userG, MollyOnNothingProvider.userGAB}) {
            Assert.assertTrue(client.canAccess(PS, user,  resourceEntry, NORMAL_ACCESS));
            Assert.assertTrue(client.canAccess(PS, user,  TypesEnums.Image, NORMAL_ACCESS));
            Assert.assertTrue(client.canAccess(PS, user,  TypesEnums.File, NORMAL_ACCESS));
            Assert.assertTrue(client.canAccess(PS, user,  TypesEnums.Menu, NORMAL_ACCESS));
        }

        {
            String user = MollyOnNothingProvider.userN;
            Assert.assertFalse(client.canAccess(PS, user,  resourceEntry, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Image, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.File, NORMAL_ACCESS));
            Assert.assertFalse(client.canAccess(PS, user,  TypesEnums.Menu, NORMAL_ACCESS));
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        for (String user : new String[]{MollyOnNothingProvider.userN, MollyOnNothingProvider.userAB, MollyOnNothingProvider.userABCD}) {
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAny() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAny);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);
        
        for (String user : new String[]{MollyOnNothingProvider.userN, MollyOnNothingProvider.userAB, MollyOnNothingProvider.userABCD}) {
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAll() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, false, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        {
            String user = MollyOnNothingProvider.userN;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userABCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_SelfControl_FitAny() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, false, ProtectionMode.FitAny);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        {
            String user = MollyOnNothingProvider.userN;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userABCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForInstanceTag_MasterControl_FitAll_FileChain() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        {
            String user = MollyOnNothingProvider.userG;
            accessChecker(client, user)
                    .multiCheck(true, TYPED_RES_REPO.docG, TYPED_RES_REPO.docG)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA)
                    .multiCheck(false, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docAC)
                    .multiCheck(true, TYPED_RES_REPO.docG, TYPED_RES_REPO.docE)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docABCD);
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            accessChecker(client, user)
                    .multiCheck(true, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docC)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docAC)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docCD)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docABC)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docACD)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docABCD)
                    .multiCheck(false, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docE, TYPED_RES_REPO.docABCDE);
        }
        {
            String user = MollyOnNothingProvider.userGABCD;

            accessChecker(client, user)
                    .multiCheck(true, TYPED_RES_REPO.docG, TYPED_RES_REPO.docA, TYPED_RES_REPO.docC, TYPED_RES_REPO.docAB, TYPED_RES_REPO.docAC, TYPED_RES_REPO.docCD, TYPED_RES_REPO.docABC, TYPED_RES_REPO.docACD, TYPED_RES_REPO.docABCD, TYPED_RES_REPO.docE, TYPED_RES_REPO.docABCDE);
        }
    }

    @Test
    public void testPermissionForInstanceTag_CheckMerge() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        final String manualUserGAB = "manual.user.gab";
        final String manualUserGAC = "manual.user.gac";
        final String manualUserGCD = "manual.user.gcd";
        final String manualUserGBD = "manual.user.gbd";
        final String manualUserGABC = "manual.user.gabc";
        {
            final ISimpleKAuthority auth4A = (ISimpleKAuthority)mollyProvider.getAuthority(userGA);
            final ISimpleKAuthority auth4B = (ISimpleKAuthority)mollyProvider.getAuthority(userGB);
            final ISimpleKAuthority auth4C = (ISimpleKAuthority)mollyProvider.getAuthority(userGC);
            final ISimpleKAuthority auth4D = (ISimpleKAuthority)mollyProvider.getAuthority(userGD);

            IKAuthority localAuthGAB = auth4A.clone().merge(auth4B);
            IKAuthority localAuthGAC = auth4A.clone().merge(auth4C);
            IKAuthority localAuthGCD = auth4C.clone().merge(auth4D);
            IKAuthority localAuthGBD = auth4B.clone().merge(auth4D);
            IKAuthority localAuthGABC = auth4A.clone().merge(auth4B).merge(auth4C);

            mollyProvider.setAuthority(manualUserGAB, localAuthGAB)
                    .setAuthority(manualUserGAC, localAuthGAC)
                    .setAuthority(manualUserGCD, localAuthGCD)
                    .setAuthority(manualUserGBD, localAuthGBD)
                    .setAuthority(manualUserGABC, localAuthGABC);
        }

        {
            String user = manualUserGAB;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = manualUserGAC;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = manualUserGCD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = manualUserGBD;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = manualUserGABC;
            accessChecker(client, user)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_MasterControl_FitAll() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        for (String user : new String[]{MollyOnNothingProvider.userN, MollyOnNothingProvider.userAB, MollyOnNothingProvider.userABCD}) {
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_MasterControl_FitAny() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAny);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        for (String user : new String[]{MollyOnNothingProvider.userN, MollyOnNothingProvider.userAB, MollyOnNothingProvider.userABCD}) {
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_SelfControl_FitAll() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, false, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        {
            String user = MollyOnNothingProvider.userN;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userAB;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userABCD;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    @Test
    public void testPermissionForQueryLike_SelfControl_FitAny() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        IKProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, false, ProtectionMode.FitAny);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        {
            String user = MollyOnNothingProvider.userN;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userAB;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userABCD;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, false)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, false)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userG;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, false)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, false)
                    .check(TYPED_RES_REPO.docAC, false)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, false)
                    .check(TYPED_RES_REPO.docACD, false)
                    .check(TYPED_RES_REPO.docABCD, false)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, false)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGAB;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, false)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, false)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
        {
            String user = MollyOnNothingProvider.userGABCD;
            KAccessibleScopeWithProtection accessibleScope = client.calcAccessibleScope(PS, user, resourceEntry, NORMAL_ACCESS);
            List<NativeDoc> results = mollyProvider.docRepo.query(accessibleScope.accessibleScope, accessibleScope.protection);
            queryLikeChecker(results)
                    .check(TYPED_RES_REPO.docG, true)
                    .check(TYPED_RES_REPO.docA, true)
                    .check(TYPED_RES_REPO.docC, true)
                    .check(TYPED_RES_REPO.docAB, true)
                    .check(TYPED_RES_REPO.docAC, true)
                    .check(TYPED_RES_REPO.docCD, true)
                    .check(TYPED_RES_REPO.docABC, true)
                    .check(TYPED_RES_REPO.docACD, true)
                    .check(TYPED_RES_REPO.docABCD, true)
                    .check(TYPED_RES_REPO.docE, true)
                    .check(TYPED_RES_REPO.docABCDE, true)
                    .ensureFullyChecked();
        }
    }

    class AccessChecker {
        private final IAuthorityVerifier client;
        private final String user;
        private int checked = 0;

        public AccessChecker(IAuthorityVerifier client, String user) {
            this.client = client;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, Serializable... instances) {
            boolean canAccess = client.canAccess(PS, user, resourceEntry, NORMAL_ACCESS, instances);

            if (allowed) {
                Assert.assertTrue(canAccess);
            } else {
                Assert.assertFalse(canAccess);
            }
            checked++;
            return this;
        }

        public AccessChecker check(Serializable instance, boolean allowed) {
            return multiCheck(allowed, instance);
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(TypedDocRepo.docCount, checked);
        }
    }

    class QueryLikeChecker {
        private List<?> results;
        private int checked = 0;

        public QueryLikeChecker(List<?> results) {
            this.results = results;
        }

        public QueryLikeChecker check(Serializable instance, boolean exist) {
            GuardedDocInstance docInstance = (GuardedDocInstance) instance;
            NativeDoc doc = docInstance.getDomainObject();
            if (exist) {
                Assert.assertTrue(results.contains(doc));
            } else {
                Assert.assertFalse(results.contains(doc));
            }
            checked++;
            return this;
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(TYPED_RES_REPO.docCount, checked);
        }
    }


}
