package com.taoswork.tallycheck.authority.provider;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermission;
import com.taoswork.tallycheck.authority.core.permission.impl.KPermissionCase;
import com.taoswork.tallycheck.authority.domain.ProtectionSpace;
import com.taoswork.tallycheck.authority.domain.ResourceAccess;
import com.taoswork.tallycheck.authority.domain.permission.Permission;
import com.taoswork.tallycheck.authority.domain.permission.PermissionCase;
import com.taoswork.tallycheck.authority.domain.resource.ProtectionLink;
import com.taoswork.tallycheck.authority.domain.user.BaseAuthority;
import com.taoswork.tallycheck.authority.provider.permission.authorities.ISimpleKAuthority;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionLink;
import com.taoswork.tallycheck.authority.provider.resource.link.IKProtectionMapping;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionLink;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionMapping;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by Gao Yuan on 2016/2/24.
 */
public class PermissionObjectMaker {
//    public static IKProtection convert(Protection sr){
//        String resource = sr.getResource();
//        KProtection protection = new KProtection(resource);
//        protection.setMasterControlled(sr.isMasterControlled());
//        protection.setProtectionMode(convert(sr.getProtectionMode()));
//        Map<String, ProtectionCase> cases = sr.getCases();
//        if(cases != null){
//            for(ProtectionCase _case : cases.values()){
//                IKProtectionCase icase = makeRcProtectionCase(resource, _case);
//                if(icase != null){
//                    protection.addCase(icase);
//                }
//            }
//        }
//        return protection;
//    }
//
//    public static IKProtectionCase makeRcProtectionCase(String resource, ProtectionCase _case){
//        ResourceProtectionCase rpc = new ResourceProtectionCase(_case.getUuid());
//        IFilter filter = FilterManager.getFilter(resource, _case.filter, _case.filterParameter);
//        rpc.setFilter(filter);
//        return rpc;
//    }

    public static ISimpleKAuthority makeAuthority(BaseAuthority authority){
        SKAuthority skAuthority = new SKAuthority(authority.getNamespace(), authority.getOwnerId());
        Map<String, Permission> permissions = authority.getPermissions();
        if(permissions != null){
            for (Permission permission : permissions.values()){
                KPermission rcp = convert(permission);
                skAuthority.addPermission(rcp);
            }
        }
        return skAuthority;
    }

    private static KPermission convert(Permission permission) {
        KPermission rcPermission = new KPermission(permission.getResource());
        rcPermission.setMasterAccess(ResourceAccess.getAsAccess(permission.getAccess()));

        Map<String, PermissionCase> cases = permission.getPermissionCases();
        if(cases != null) {
            for (PermissionCase _case : permission.getPermissionCases().values()) {
                String code = _case.getCode();
                Access access = ResourceAccess.getAsAccess(_case.getAccess());
                KPermissionCase pcs = new KPermissionCase(code, access);
                rcPermission.addCase(pcs);
            }
        }
        return rcPermission;
    }

    public static IKProtectionMapping convert(ProtectionSpace ps){
        final KProtectionMapping mapping = new KProtectionMapping();
        Map<String, String[]> aliases = ps.getAliases();
        if(aliases != null){
            aliases.forEach(new BiConsumer<String, String[]>() {
                @Override
                public void accept(String s, String[] strings) {
                    mapping.registerAlias(s, strings);
                }
            });
        }
        List<ProtectionLink> protectionLinks = ps.getProtectionLinks();
        if(protectionLinks != null){
            protectionLinks.forEach(new Consumer<ProtectionLink>() {
                @Override
                public void accept(ProtectionLink protectionLink) {
                    mapping.registerLink(convert(protectionLink));
                }
            });
        }
        return mapping;
    }
    public static IKProtectionLink convert(ProtectionLink pl){
        return new KProtectionLink(pl.getVirtualResource(),
                pl.getVirtualAccess(), pl.actualResource,
                pl.getActualAccess(), pl.getProtectionMode());
    }


}
