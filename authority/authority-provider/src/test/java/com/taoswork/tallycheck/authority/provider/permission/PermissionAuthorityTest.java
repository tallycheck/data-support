package com.taoswork.tallycheck.authority.provider.permission;

import com.taoswork.tallycheck.authority.atom.Access;
import com.taoswork.tallycheck.authority.provider.onnothing.Mockuper;
import com.taoswork.tallycheck.authority.provider.onnothing.common.TypesEnums;
import com.taoswork.tallycheck.authority.provider.permission.authorities.ISimpleKAuthority;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Gao Yuan on 2015/8/20.
 */
public class PermissionAuthorityTest {

    @Test
    public void testClone() {
        //test clone
        ISimpleKAuthority authDocA = Mockuper.authorityWith(TypesEnums.DOC, Access.Read, true, false, true, false, true);
        ISimpleKAuthority authDocB = Mockuper.authorityWith(TypesEnums.DOC, Access.Read, false, true, false, true, false);
        {
            Assert.assertNotEquals(authDocA, authDocB);
            IKAuthority authDocAClone = authDocA.clone();
            Assert.assertEquals(authDocA, authDocAClone);
        }


    }

    void assertElementsEqual(Object... array) {
        if (array.length == 0) {
            return;
        }
        for (int i = 1; i < array.length; ++i) {
            Assert.assertEquals(array[0], array[i]);
        }
    }

    @Test
    public void testMerge() {
        ISimpleKAuthority authDocA = Mockuper.authorityWith(TypesEnums.DOC, Access.Read, true, false, true, false, true);
        ISimpleKAuthority authDocB = Mockuper.authorityWith(TypesEnums.DOC, Access.Read, false, true, false, true, false);
        ISimpleKAuthority authDocAB = Mockuper.authorityWith(TypesEnums.DOC, Access.Read, true, true, true, true, true);
        ISimpleKAuthority authImgA = Mockuper.authorityWith(TypesEnums.Image, Access.Create, true, false, true, false, true);
        ISimpleKAuthority authImgB = Mockuper.authorityWith(TypesEnums.Image, Access.Create, false, true, false, true, false);
        ISimpleKAuthority authImgAB = Mockuper.authorityWith(TypesEnums.Image, Access.Create, true, true, true, true, true);
        {
            IKAuthority authDocABByMerge = authDocA.clone().merge(authDocB);
            Assert.assertEquals(authDocAB, authDocABByMerge);
        }
        {
            //docA + docB + imgA
            IKAuthority o1 = authDocA.clone().merge(authDocB).merge(authImgA);
            IKAuthority o2 = authDocA.clone().merge(authDocAB).merge(authImgA);
            IKAuthority o3 = authDocAB.clone().merge(authImgA);
            IKAuthority o4 = authDocAB.clone().merge(authDocA).merge(authImgA);
            assertElementsEqual(o1, o2, o3, o4);
        }
        {
            //docA + imgA
            IKAuthority o1 = authDocA.clone().merge(authImgA);
            IKAuthority o2 = authImgA.clone().merge(authDocA);
            IKAuthority o3 = authImgA.clone().merge(authImgA).merge(authDocA);
            IKAuthority o4 = authDocA.clone().merge(authDocA).merge(authImgA);
            assertElementsEqual(o1, o2, o3, o4);
        }
        {
            //docA + docB + imgA + imgB
            IKAuthority o1 = authDocA.clone().merge(authDocB).merge(authImgA).merge(authImgB);
            IKAuthority o2 = authDocAB.clone().merge(authImgAB);
            IKAuthority o3 = authDocAB.clone().merge(authImgA).merge(authImgB);
            IKAuthority o4 = authDocA.clone().merge(authDocB).merge(authImgAB);
            assertElementsEqual(o1, o2, o3, o4);
        }

    }
}
