package com.taoswork.tallycheck.authority.domain.user;

import com.taoswork.tallycheck.authority.atom.utility.ResourceUtility;
import com.taoswork.tallycheck.authority.domain.permission.Permission;
import com.taoswork.tallycheck.datadomain.base.entity.MapField;
import com.taoswork.tallycheck.datadomain.base.entity.MapMode;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import org.mongodb.morphia.annotations.Field;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Version;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Gao Yuan on 2016/2/7.
 */
@PersistEntity(
)
@Indexes({
        @Index(fields = @Field(BaseAuthority.FN_PROTECTION_SPEC)),
        @Index(fields = {@Field(BaseAuthority.FN_PROTECTION_SPEC),
                @Field(BaseAuthority.FN_PROTECTION_REGION)}),
        @Index(fields = {@Field(BaseAuthority.FN_PROTECTION_SPEC),
                @Field(BaseAuthority.FN_OWNER_ID),
                @Field(BaseAuthority.FN_PROTECTION_REGION)}, unique = true),
        @Index(fields = {@Field(BaseAuthority.FN_OWNER_ID)})
})
public abstract class BaseAuthority
        extends AbstractDocument {

    @PersistField(fieldType = FieldType.STRING, required = true)
    @PresentationField(order = 2, visibility = Visibility.HIDDEN_ALL)
    protected String protectionSpec;
    public static final String FN_PROTECTION_SPEC = "protectionSpec";

    @PersistField(fieldType = FieldType.STRING, required = true)
    protected String protectionRegion;
    public static final String FN_PROTECTION_REGION = "protectionRegion";

    private String ownerId;
    public static final String FN_OWNER_ID = "ownerId";

    @MapField(mode = MapMode.Entity, keyFieldOnValue = Permission.FN_RESOURCE)
    //the key is resource name; ResourceUtility.unifiedResourceName(resource);
    protected Map<String, Permission> permissions = new HashMap<String, Permission>();

    @Version
    protected Long version = null;

    public String getProtectionSpec() {
        return protectionSpec;
    }

    public void setProtectionSpec(String protectionSpec) {
        this.protectionSpec = protectionSpec;
    }

    public String getProtectionRegion() {
        return protectionRegion;
    }

    public void setProtectionRegion(String protectionRegion) {
        this.protectionRegion = protectionRegion;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Map<String, Permission> getPermissions() {
        return permissions;
    }

    public Permission getPermission(String resource) {
        String uRes = ResourceUtility.unifiedResourceName(resource);
        return permissions.get(uRes);
    }

    public void addPermission(Permission permission) {
        String uRes =  ResourceUtility.unifiedResourceName(permission.getResource());
        this.permissions.put(uRes, permission);
    }

    public void setPermissions(Map<String, Permission> permissions) {
        if(permissions == null)
            throw new IllegalArgumentException("Null map not supported");
        this.permissions = permissions;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getInstanceName() {
        return null;
    }
}
