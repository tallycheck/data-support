package com.taoswork.tallycheck.authority.provider.onnothing.provider;

import com.taoswork.tallycheck.authority.core.permission.KAccessibleScope;
import com.taoswork.tallycheck.authority.core.resource.IKProtection;
import com.taoswork.tallycheck.authority.core.resource.IKProtectionCase;
import com.taoswork.tallycheck.authority.provider.onnothing.common.NativeDoc;
import com.taoswork.tallycheck.authority.provider.onnothing.client.ICoreAccessSensitiveRepo;

import java.util.*;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class DocRepo implements ICoreAccessSensitiveRepo<NativeDoc> {
    private Map<String, List<NativeDoc>> documentsByType = new HashMap<String, List<NativeDoc>>();

    protected List<NativeDoc> getDocsByType(String type){
        List<NativeDoc> docs = documentsByType.get(type);
        if (docs == null){
            docs = new ArrayList<NativeDoc>();
            documentsByType.put(type, docs);
        }
        return docs;
    }

    public void pushIn(String type, NativeDoc doc) {
        List<NativeDoc> docs = getDocsByType(type);
        docs.add(doc);
    }

    @Override
    public List<NativeDoc> query(KAccessibleScope accessibleScope, IKProtection resourceProtection) {
        List<NativeDoc> result = new ArrayList<NativeDoc>();
        if (accessibleScope == null) {
            return result;
        }
        List<NativeDoc> docs = getDocsByType(resourceProtection.getResource());

        Collection<String> passCasesCode = accessibleScope.passCases;
        Collection<String> blockCasesCode = accessibleScope.blockCases;

        Collection<IKProtectionCase> cases = resourceProtection.getCases();
        Set<IKProtectionCase> passCases = (passCasesCode != null ? new HashSet<IKProtectionCase>() : null);
        Set<IKProtectionCase> blockCases = (blockCasesCode != null ? new HashSet<IKProtectionCase>() : null);

        for (IKProtectionCase _case : cases) {
            if (passCasesCode != null && passCasesCode.contains(_case.getCode())) {
                passCases.add(_case);
            } else if (blockCasesCode != null && blockCasesCode.contains(_case.getCode())) {
                blockCases.add(_case);
            }
        }

        if (accessibleScope.inUnionMode) {
            for (NativeDoc doc : docs) {
                GuardedDocInstance docInstance = new GuardedDocInstance(doc);
                boolean fit = false;
                if (passCases == null) {
                    fit = true;
                } else {
                    for (IKProtectionCase passCase : passCases) {
                        if (passCase.isMatch(docInstance)) {
                            fit = true;
                            break;
                        }
                    }
                }
                if (!fit) {
                    boolean blockFit = false;
                    if (blockCases == null) {
                        blockFit = false;
                    } else {
                        for (IKProtectionCase blockCase : blockCases) {
                            if (blockCase.isMatch(docInstance)) {
                                blockFit = true;
                                break;
                            }
                        }
                    }
                    fit = !blockFit;
                }
                if (fit) {
                    result.add(doc);
                }
            }
        } else {
            for (NativeDoc doc : docs) {
                GuardedDocInstance docInstance = new GuardedDocInstance(doc);
                boolean fit = false;
                if (passCases == null) {
                    fit = true;
                } else {
                    for (IKProtectionCase passCase : passCases) {
                        if (passCase.isMatch(docInstance)) {
                            fit = true;
                            break;
                        }
                    }
                }
                if (fit) {
                    if (blockCases == null) {
                        fit = true;
                    } else {
                        for (IKProtectionCase blockCase : blockCases) {
                            if (blockCase.isMatch(docInstance)) {
                                fit = false;
                                break;
                            }
                        }
                    }
                }
                if (fit) {
                    result.add(doc);
                }
            }
        }
        return result;
    }
}
