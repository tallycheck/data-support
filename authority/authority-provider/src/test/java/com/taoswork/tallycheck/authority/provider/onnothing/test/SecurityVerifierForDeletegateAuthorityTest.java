package com.taoswork.tallycheck.authority.provider.onnothing.test;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.client.AccessClient;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.provider.onnothing.client.MollyOnNothingClient;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.MollyOnNothingProvider;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.TypedDocRepo;
import com.taoswork.tallycheck.authority.provider.permission.IKAuthority;
import com.taoswork.tallycheck.authority.provider.permission.authorities.DelegateKAuthority;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionMapping;
import com.taoswork.tallycheck.authority.provider.onnothing.common.TypesEnums;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierForDeletegateAuthorityTest {
    private final String resourceEntry = TypesEnums.DOC;
    private final Access NORMAL_ACCESS = Access.Read;
    private final TypedDocRepo TYPED_RES_REPO = new TypedDocRepo(resourceEntry);
    private final ProtectionScope PS = null;

    private static final String userGA = "user:GA:11000";
    private static final String userGB = "user:GB:10100";
    private static final String userGC = "user:GC:10010";
    private static final String userGD = "user:GD:10001";


    private AccessChecker accessChecker(AccessClient client, String user) {
        return new AccessChecker(client, user);
    }

    @Test
    public void testPermissionForInstanceTag_CheckDelegate() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        KProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(resourceEntry, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);


        final String manualUserGAB = "manual.user.gab";
        final String manualUserGAC = "manual.user.gac";
        final String manualUserGCD = "manual.user.gcd";
        final String manualUserGBD = "manual.user.gbd";
        final String manualUserGABC = "manual.user.gabc";
        {
            final IKAuthority auth4MergeTestA = mollyProvider.getAuthority(userGA);
            final IKAuthority auth4MergeTestB = mollyProvider.getAuthority(userGB);
            final IKAuthority auth4MergeTestC = mollyProvider.getAuthority(userGC);
            final IKAuthority auth4MergeTestD = mollyProvider.getAuthority(userGD);

            IKAuthority localAuthGAB = new DelegateKAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestB);
            IKAuthority localAuthGAC = new DelegateKAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestC);
            IKAuthority localAuthGCD = new DelegateKAuthority().addAuthority(auth4MergeTestC).addAuthority(auth4MergeTestD);
            IKAuthority localAuthGBD = new DelegateKAuthority().addAuthority(auth4MergeTestB).addAuthority(auth4MergeTestD);
            IKAuthority localAuthGABC = new DelegateKAuthority().addAuthority(auth4MergeTestA).addAuthority(auth4MergeTestB).addAuthority(auth4MergeTestC);

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

    class AccessChecker {
        private final AccessClient client;
        private final String user;
        private int checked = 0;

        public AccessChecker(AccessClient client, String user) {
            this.client = client;
            this.user = user;
        }

        public AccessChecker multiCheck(boolean allowed, Serializable... instances) {
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
            return multiCheck(allowed, instance);
        }

        public void ensureFullyChecked() {
            Assert.assertEquals(TYPED_RES_REPO.docCount, checked);
        }
    }
}