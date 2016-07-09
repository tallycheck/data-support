package com.taoswork.tallycheck.authority.domain;

import com.taoswork.tallycheck.authority.domain.resource.ProtectionLink;
import com.taoswork.tallycheck.datadomain.base.entity.*;
import com.taoswork.tallycheck.datadomain.onmongo.AbstractDocument;
import org.apache.commons.lang3.ArrayUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


/**
 * Created by Gao Yuan on 2016/2/24.
 */
@Entity
@PersistEntity
public class ProtectionSpace extends AbstractDocument{
    @Indexed(unique = true)
    private String spaceName;
    public static final String FN_SPACE_NAME = "spaceName";

    //Key is actual resource
    @MapField(mode = MapMode.Sloth)
    private Map<String, String[]> aliases = new HashMap<String, String[]>();

    @CollectionField(mode = CollectionMode.Basic)
    private List<ProtectionLink> protectionLinks;

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public void addAliases(String resource, final String[] _aliases){
        if(aliases == null){
            aliases = new HashMap<String, String[]>();
        }
        aliases.compute(resource, new BiFunction<String, String[], String[]>() {
            @Override
            public String[] apply(String s, String[] strings) {
                if(strings == null)
                    return _aliases;
                return ArrayUtils.addAll(strings, _aliases);
            }
        });
    }

    public Map<String, String[]> getAliases() {
        return aliases;
    }

    public void setAliases(Map<String, String[]> aliases) {
        this.aliases = aliases;
    }

    public List<ProtectionLink> getProtectionLinks() {
        return protectionLinks;
    }

    public void setProtectionLinks(List<ProtectionLink> protectionLinks) {
        this.protectionLinks = protectionLinks;
    }

    @Override
    public String getInstanceName() {
        return getSpaceName();
    }
}
