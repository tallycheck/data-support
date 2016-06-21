package com.taoswork.tallycheck.dataservice.jpa.servicemockup.service.dynamic;

import com.taoswork.tallycheck.dataservice.jpa.core.metaaccess.BaseJpaEntityMetaAccess;
import com.taoswork.tallycheck.dataservice.jpa.servicemockup.datasource.TallyMockupJpaDatasourceDefinition;
import com.taoswork.tallycheck.dataservice.service.EntityMetaAccess;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Gao Yuan on 2015/6/2.
 */
@Component(EntityMetaAccess.COMPONENT_NAME)
public class TallyMockupEntityMetaAccess extends BaseJpaEntityMetaAccess {
    @PersistenceContext(name = TallyMockupJpaDatasourceDefinition.TMOCKUP_PU_NAME)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

}
