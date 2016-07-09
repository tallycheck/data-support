package com.taoswork.tallycheck.authority.domain.user;

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
        @Index(fields = @Field(BaseAuthority.FN_PROTECTION_SPACE)),
        @Index(fields = {@Field(BaseAuthority.FN_PROTECTION_SPACE),
                @Field(BaseAuthority.FN_NAMESPACE)}),
        @Index(fields = {@Field(BaseAuthority.FN_PROTECTION_SPACE),
                @Field(BaseAuthority.FN_OWNER_ID),
                @Field(BaseAuthority.FN_NAMESPACE)}, unique = true),
        @Index(fields = {@Field(BaseAuthority.FN_OWNER_ID)})
})
public abstract class BaseAuthority
        extends AbstractDocument {

    @PersistField(fieldType = FieldType.STRING, required = true)
    @PresentationField(order = 2, visibility = Visibility.HIDDEN_ALL)
    protected String protectionSpace;
    public static final String FN_PROTECTION_SPACE = "protectionSpace";

    @PersistField(fieldType = FieldType.STRING, required = true)
    protected String namespace;
    public static final String FN_NAMESPACE = "namespace";

    private String ownerId;
    public static final String FN_OWNER_ID = "ownerId";

    @MapField(mode = MapMode.Entity, keyFieldOnValue = Permission.FN_RESOURCE)
    //the key is resource name;
    protected Map<String, Permission> permissions = new HashMap<String, Permission>();

    @Version
    protected Long version = null;

    public String getProtectionSpace() {
        return protectionSpace;
    }

    public void setProtectionSpace(String protectionSpace) {
        this.protectionSpace = protectionSpace;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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
        return permissions.get(resource);
    }

    public void addPermission(Permission permission) {
        this.permissions.put(permission.getResource(), permission);
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
