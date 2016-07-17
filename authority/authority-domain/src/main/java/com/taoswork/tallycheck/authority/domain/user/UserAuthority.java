package com.taoswork.tallycheck.authority.domain.user;

import com.taoswork.tallycheck.datadomain.base.entity.PersistField;
import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.datadomain.base.presentation.PresentationField;
import com.taoswork.tallycheck.datadomain.base.presentation.Visibility;
import org.mongodb.morphia.annotations.Entity;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/10.
 */
@Entity
public abstract class UserAuthority<GA extends GroupAuthority> extends BaseAuthority {
    public abstract Collection<? extends GroupAuthority> theGroups();

    @PersistField(fieldType = FieldType.BOOLEAN, required = true)
    @PresentationField(order = 50, visibility = Visibility.HIDDEN_ALL)
    private boolean superUser = false;

    public boolean isSuperUser() {
        return superUser;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }
}
