package com.taoswork.tallycheck.authority.domain.resource;

import com.taoswork.tallycheck.authority.domain.resource.protection.ProtectionGate;
import com.taoswork.tallycheck.authority.domain.resource.protection.ProtectionValidator;
import com.taoswork.tallycheck.datadomain.base.entity.MapField;
import com.taoswork.tallycheck.datadomain.base.entity.MapMode;
import com.taoswork.tallycheck.datadomain.base.entity.PersistEntity;
import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationClass;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.BooleanMode;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.PresentationBoolean;
import com.taoswork.tallycheck.datadomain.base.presentation.typed.PresentationEnum;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Gao Yuan on 2015/6/5.
 */
@Entity
@Indexes(
        {
                @Index(value = "protectionSpec", fields ={
                        @Field(Protection.FN_PROTECTION_SPEC)}),
                @Index(fields = @Field(Protection.FN_PROTECTION_REGION)),
                @Index(value = "scopedResource", fields = {
                        @Field(Protection.FN_PROTECTION_SPEC),
                        @Field(Protection.FN_PROTECTION_REGION),
                        @Field("resource")
                }, unique = true)
        }
)
@PersistEntity(asDefaultPermissionGuardian = true,
        validators = ProtectionValidator.class,
        valueGates = ProtectionGate.class
)
@PresentationClass()
public class Protection
        extends AbstractDocument {

    @PersistField(fieldType = FieldType.STRING, required = true)
    @PresentationField(order = 3, visibility = Visibility.HIDDEN_ALL)
    protected String protectionSpec;
    public static final String FN_PROTECTION_SPEC = "protectionSpec";

    private String protectionRegion;
    public static final String FN_PROTECTION_REGION = "protectionRegion";

    @PersistField(fieldType = FieldType.NAME, required = true, length = 100)
    @PresentationField(order = 1)
    protected String name;

    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 2, visibility = Visibility.GRID_HIDE)
    protected String description;

    @Indexed
    @PersistField(fieldType = FieldType.STRING, required = true)
    @PresentationField(order = 3)
    protected String resource;
    public static final String FN_RESOURCE_ENTITY = "resource";

    @PersistField(fieldType = FieldType.STRING)
    @PresentationField(order = 4)
    protected String category;

    @PersistField(fieldType = FieldType.ENUMERATION, required = true, length = 10)
    @PresentationField(order = 5)
    @PresentationEnum(enumeration = DProtectionMode.class)
    protected DProtectionMode protectionMode;

    @PersistField(fieldType = FieldType.BOOLEAN, required = true)
    @PresentationField(order = 6)
    @PresentationBoolean(mode = BooleanMode.YesNo)
    protected Boolean masterControlled = Boolean.TRUE;

    //key uses ProtectionCase's unique code
    @MapField(mode = MapMode.Entity, keyFieldOnValue = "uuid")
    private Map<String, ProtectionCase> cases = new HashMap<String, ProtectionCase>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(Class resource) {
        if(null == resource)
            setResource("");
        setResource(resource.getName());
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isMasterControlled() {
        return masterControlled;
    }

    public void setMasterControlled(Boolean masterControlled) {
        this.masterControlled = masterControlled;
    }

    public DProtectionMode getProtectionMode() {
        return protectionMode;
    }

    public void setProtectionMode(DProtectionMode protectionMode) {
        this.protectionMode = protectionMode;
    }

    public ProtectionCase getResourceCase(String caseCode) {
        return cases.get(caseCode);
    }

    public Map<String, ProtectionCase> getCases() {
        return cases;
    }

    public void addCase(ProtectionCase _case) {
        if(StringUtils.isBlank(_case.getUuid())){
            _case.setUuid(UUID.randomUUID().toString());
        }
        cases.put(_case.getUuid(), _case);
    }

    public void setCases(Map<String, ProtectionCase> cases) {
        this.cases = cases;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version == null ? 0 : version;
    }

    @Override
    public String getInstanceName() {
        return null;
    }
}
