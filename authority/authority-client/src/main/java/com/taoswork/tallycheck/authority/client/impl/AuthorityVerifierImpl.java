package com.taoswork.tallycheck.authority.client.impl;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.client.IAuthorityVerifier;
import com.taoswork.tallycheck.authority.client.KAccessibleScopeWithProtection;
import com.taoswork.tallycheck.authority.client.KCaseFitting;
import com.taoswork.tallycheck.authority.client.filter.EntityFilterManager;
import com.taoswork.tallycheck.authority.core.IllegalCodePathException;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.core.permission.IKPermission;
import com.taoswork.tallycheck.authority.core.permission.KAccessibleScope;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.core.resource.IKProtectionCase;
import com.taoswork.tallycheck.authority.provider.IAuthorityProvider;
import com.taoswork.tallycheck.authority.provider.ResProtection;
import com.taoswork.tallycheck.authority.provider.ResProtectionWithPermission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gaoyuan on 7/2/16.
 */
public class AuthorityVerifierImpl implements IAuthorityVerifier {
    private IAuthorityProvider provider;
    protected final EntityFilterManager filterManager;

    public AuthorityVerifierImpl(IAuthorityProvider provider, EntityFilterManager filterManager) {
        this.provider = provider;
        this.filterManager = filterManager;
    }

    public void setProvider(IAuthorityProvider provider){
        this.provider = provider;
    }

    @Override
    public Access getAllPossibleAccess(ProtectionScope scope, String userId, String resourceTypeName, Access mask) {
        IKPermission permission = provider.getPermission(scope, resourceTypeName, userId);
        if (permission == null) {
            return Access.None;
        }
        Access access = permission.getQuickCheckAccess();
        if (mask == null) {
            return access;
        } else {
            return mask.and(access);
        }
    }

    @Override
    public boolean canAccess(ProtectionScope scope, String userId, String resourceTypeName, Access requiredAccess) {
        IKPermission permission = provider.getPermission(scope, resourceTypeName, userId);
        if (permission == null) {
            return false;
        }
        Access access = permission.getQuickCheckAccess();
        return access.hasAccess(requiredAccess);
    }

    @Override
    public boolean canAccess(ProtectionScope scope, String userId, String resourceTypeName, Access requiredAccess,
                             Object... instances) {
        ResProtectionWithPermission resProtectionWithPermission = provider.getProtectionWithPermission(scope, resourceTypeName, userId);
        if (resProtectionWithPermission == null) {
            return false;
        }
        ResProtection resProtection = resProtectionWithPermission.resProtection;
        if (resProtection == null) {
            return false;
        }
        IKPermission permission = resProtectionWithPermission.permission;
        IKProtection protection = AuthorityObjectMaker.convert(filterManager, resProtection);
        if (permission == null) {
            return false;
        }
        KCaseFitting fitting = getFitting(protection, true, instances);
        Access mergedAccess = permission.getAccessByCases(fitting.matching,
                fitting.isMasterControlled, fitting.protectionMode);
        return mergedAccess.hasAccess(requiredAccess);
    }

    @Override
    public KAccessibleScopeWithProtection calcAccessibleScope(ProtectionScope scope, String userId, String resourceTypeName, Access access) {
        ResProtectionWithPermission resProtectionWithPermission = provider.getProtectionWithPermission(scope, resourceTypeName, userId);
        ResProtection resProtection = resProtectionWithPermission.resProtection;
        IKPermission permission = resProtectionWithPermission.permission;
        IKProtection protection = AuthorityObjectMaker.convert(filterManager, resProtection);

        if (permission == null)
            return null;
        final boolean hasMasterAccess = permission.getMasterAccess().hasAccess(access);
        final List<String> matchingCases = new ArrayList<String>();
        final List<String> unmatchedCases = new ArrayList<String>();
        for (IKProtectionCase _case : protection.getCases()) {
            boolean hasAccess = permission.getCaseAccess(_case.getCode()).hasAccess(access);
            (hasAccess ? matchingCases : unmatchedCases).add(_case.getCode());
        }

        final KAccessibleScope accessibleScope;

        switch (protection.getProtectionMode()) {
            case FitAll:
                if (protection.isMasterControlled()) {
                    // C11
                    if (hasMasterAccess) {
                        //i04, i05, i06
                        accessibleScope = new KAccessibleScope(null, unmatchedCases);
                    } else {
                        //i01, i02, i03
                        accessibleScope = null;
                    }
                } else {
                    // C01
                    if (hasMasterAccess) {
                        //i04, i05, i06
                        accessibleScope = new KAccessibleScope(null, unmatchedCases);
                    } else if (matchingCases.isEmpty()) {
                        //i01
                        accessibleScope = null;
                    } else {
                        //i02, i03
                        accessibleScope = new KAccessibleScope(matchingCases, unmatchedCases);
                    }
                }
                break;

            case FitAny:
                if (protection.isMasterControlled()) {
                    // C10
                    if (hasMasterAccess) {
                        if (matchingCases.isEmpty()) {
                            //i04
                            accessibleScope = new KAccessibleScope(null, unmatchedCases);
                        } else if (unmatchedCases.isEmpty()) {
                            //i06
                            accessibleScope = new KAccessibleScope(null, null);
                        } else {
                            //i05
                            accessibleScope = new KAccessibleScope(matchingCases, unmatchedCases, true);
                        }
                    } else {
                        //i01, i02, i03
                        accessibleScope = null;
                    }
                } else {
                    // C00
                    if (hasMasterAccess) {
                        if (matchingCases.isEmpty()) {
                            //i04
                            accessibleScope = new KAccessibleScope(null, unmatchedCases); //can also handle i06
                        } else if (unmatchedCases.isEmpty()) {
                            //i06
                            accessibleScope = new KAccessibleScope(null, null);
                        } else {
                            //i05
                            accessibleScope = new KAccessibleScope(matchingCases, unmatchedCases, true);
                        }
                    } else {
                        if (matchingCases.isEmpty()) {
                            //i01
                            accessibleScope = null;
                        } else {
                            //i02, i03
                            accessibleScope = new KAccessibleScope(matchingCases, null);
                        }
                    }
                }
                break;
            default:
                throw new IllegalCodePathException("Everything should be handled, should never be here.");
        }
        return new KAccessibleScopeWithProtection(accessibleScope, protection);
    }

    /**
     * Work out the KCaseFitting of the instances
     *
     * @param protection
     * @param matchingPreferred, if true:
     *                           if any instance fit the case, the case in the KCaseFitting will be marked as true;
     *                           if false:
     *                           if any instance doesn't fit the case, the case in the KCaseFitting will be marked as false;
     * @param instances,         instances to be checked.
     * @return
     */
    protected KCaseFitting getFitting(IKProtection protection, boolean matchingPreferred, Object... instances) {
        if (instances.length == 0) {
            throw new IllegalArgumentException();
        }

        Set<String> matchingCasesCode = new HashSet<String>();
        Set<String> unmatchedCasesCode = new HashSet<String>();
        for (IKProtectionCase _case : protection.getCases()) {
            boolean matching = false;
            boolean unmatched = false;
            for (Object ins : instances) {
                if (_case.isMatch(ins)) {
                    matching = true;
                } else {
                    unmatched = true;
                }
            }
//            if ((matchingPreferred && matching) || (!(matchingPreferred || unmatched))) {
//                matchingCasesCode.add(_case.getCode());
//            } else {
//                unmatchedCasesCode.add(_case.getCode());
//            }
            if (matchingPreferred) {
                if (matching) {
                    matchingCasesCode.add(_case.getCode());
                } else {
                    unmatchedCasesCode.add(_case.getCode());
                }
            } else { // !matchingPreferred
                if (unmatched) {
                    unmatchedCasesCode.add(_case.getCode());
                } else {
                    matchingCasesCode.add(_case.getCode());
                }
            }

//            Tip:
//            ((matchingPreferred && matching) || ((!matchingPreferred) && (!unmatched)))
//                ===
//            ((matchingPreferred && matching) || (!(matchingPreferred || unmatched)))
        }
        return new KCaseFitting(
                protection.getResource(),
                protection.isMasterControlled(),
                protection.getProtectionMode(),
                matchingCasesCode, unmatchedCasesCode);
    }

    @Override
    public boolean canAccessMappedResource(ProtectionScope scope, String userId, String virtualResource, Access requiredAccess) {
        return provider.canAccessMappedResource(scope, virtualResource, requiredAccess, userId);
    }
}
