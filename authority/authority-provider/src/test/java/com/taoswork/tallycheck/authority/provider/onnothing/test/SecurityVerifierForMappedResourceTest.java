package com.taoswork.tallycheck.authority.provider.onnothing.test;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.atom.ProtectionMode;
import com.taoswork.tallycheck.authority.core.ProtectionScope;
import com.taoswork.tallycheck.authority.provider.onnothing.client.MollyOnNothingClient;
import com.taoswork.tallycheck.authority.provider.onnothing.common.TypesEnums;
import com.taoswork.tallycheck.authority.provider.onnothing.provider.MollyOnNothingProvider;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionLink;
import com.taoswork.tallycheck.authority.provider.resource.link.KProtectionMapping;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/19.
 */
public class SecurityVerifierForMappedResourceTest {
    private final String RESOURCE_ENTITY = TypesEnums.DOC;
    private final Access NORMAL_ACCESS = Access.Read;
    private final ProtectionScope PS = null;

    private final String docMenu = "menu.doc";
    private final Access menuClick = Access.makeExtendedAccess(0x1);
    private final Access menuDbClick = Access.makeExtendedAccess(0x2);
    private final Access menuVisible = Access.makeExtendedAccess(0x4);

    private final String imgMenu = "menu.img";

    private final KProtectionLink protectDocMenuAnyAction = new KProtectionLink(
            docMenu, menuVisible, RESOURCE_ENTITY, Access.Read.merge(Access.Create), ProtectionMode.FitAny);
    private final KProtectionLink protectDocMenuClick = new KProtectionLink(
            docMenu, menuClick, RESOURCE_ENTITY, Access.Read);
    private final KProtectionLink protectDocMenuDbClick = new KProtectionLink(
            docMenu, menuDbClick, RESOURCE_ENTITY, Access.Update);

    private final KProtectionLink protectImgMenuClick = new KProtectionLink(
            imgMenu, menuClick, TypesEnums.Image, Access.Read);

    @Test
    public void testPermissionForType() {
        final MollyOnNothingProvider mollyProvider = new MollyOnNothingProvider(NORMAL_ACCESS);
        KProtectionMapping protectionMapping = new KProtectionMapping();
        mollyProvider.registerProtectionMapping(protectionMapping);
        mollyProvider.registerProtection(RESOURCE_ENTITY, true, ProtectionMode.FitAll);
        MollyOnNothingClient client = new MollyOnNothingClient(mollyProvider);

        protectionMapping
                .registerLink(protectDocMenuAnyAction)
                .registerLink(protectDocMenuClick)
                .registerLink(protectDocMenuDbClick)
                .registerLink(protectImgMenuClick);


        for (String user : new String[]{MollyOnNothingProvider.userAB, MollyOnNothingProvider.userG, MollyOnNothingProvider.userGAB}) {
            Assert.assertTrue(client.canAccessMappedResource(PS, user,docMenu, menuVisible));
            Assert.assertTrue(client.canAccessMappedResource(PS, user,docMenu, menuClick));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,docMenu, menuDbClick));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,imgMenu, menuClick));

            Assert.assertFalse(client.canAccessMappedResource(PS, user,TypesEnums.Image, menuVisible));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,TypesEnums.File, menuVisible));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,TypesEnums.Menu, menuVisible));
        }

        {
            String user = MollyOnNothingProvider.userN;
            Assert.assertFalse(client.canAccessMappedResource(PS, user,docMenu, menuVisible));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,docMenu, menuClick));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,docMenu, menuDbClick));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,imgMenu, menuClick));

            Assert.assertFalse(client.canAccessMappedResource(PS, user,TypesEnums.Image, menuVisible));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,TypesEnums.File, menuVisible));
            Assert.assertFalse(client.canAccessMappedResource(PS, user,TypesEnums.Menu, menuVisible));
        }
    }
}