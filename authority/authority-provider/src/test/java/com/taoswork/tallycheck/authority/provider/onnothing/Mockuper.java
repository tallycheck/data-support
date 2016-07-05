package com.taoswork.tallycheck.authority.provider.onnothing;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermission;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermissionCase;
import com.taoswork.tallycheck.authority.provider.ResProtectionCase;
import com.taoswork.tallycheck.authority.provider.onnothing.client.ByTagFilter;
import com.taoswork.tallycheck.authority.provider.onnothing.common.NativeDoc;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.GuardedDocInstance;
import com.taoswork.tallycheck.authority.provider.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallycheck.authority.provider.permission.authorities.SimpleKAuthority;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class Mockuper {
    public static final String TAGA = "a";
    public static final String TAGB = "b";
    public static final String TAGC = "c";
    public static final String TAGD = "d";
    public static final String TAGE = "e";

    public static final String FILTER_NAME = ByTagFilter.TYPE_NAME;

    public static GuardedDocInstance docWith(boolean a, boolean b, boolean c, boolean d, boolean e) {
        StringBuilder sb = new StringBuilder();
        if (a) sb.append(TAGA);
        if (b) sb.append(TAGB);
        if (c) sb.append(TAGC);
        if (d) sb.append(TAGD);
        if (e) sb.append(TAGE);
        NativeDoc doc = new NativeDoc(sb.toString(), null);
        if (a) doc.addTag(TAGA);
        if (b) doc.addTag(TAGB);
        if (c) doc.addTag(TAGC);
        if (d) doc.addTag(TAGD);
        if (e) doc.addTag(TAGE);
        return new GuardedDocInstance(doc);
    }

    public static ResProtectionCase protectionCase(String tag){
        ResProtectionCase _case = new ResProtectionCase();
        _case.code = tag;
        _case.filterType = FILTER_NAME;
        _case.filterParameter = tag;
        return _case;
    }

    public static KPermissionCase permissionCase(ResProtectionCase protectionCase, Access access){
        return new KPermissionCase(protectionCase.code, access);
    }

    protected static KPermission permissionWith(String resource, Access normalAccess, boolean g, boolean a, boolean b, boolean c, boolean d) {
        KPermission entityPermission = new KPermission(resource);
        KPermissionCase accessA = new KPermissionCase(TAGA, normalAccess);
        KPermissionCase accessB = new KPermissionCase(TAGB, normalAccess);
        KPermissionCase accessC = new KPermissionCase(TAGC, normalAccess);
        KPermissionCase accessD = new KPermissionCase(TAGD, normalAccess);

        entityPermission.addCases(
                a ? accessA : null,
                b ? accessB : null,
                c ? accessC : null,
                d ? accessD : null);

        entityPermission.setMasterAccess(g ? normalAccess : Access.None);

        return entityPermission;
    }

    public static ISimpleKAuthority authorityWith(String resource, Access normalAccess, boolean g, boolean a, boolean b, boolean c, boolean d) {
        KPermission permission = permissionWith(resource, normalAccess, g, a, b, c, d);
        ISimpleKAuthority simpleKAuthority = new SimpleKAuthority().addPermission(permission);
        return simpleKAuthority;
    }

}
