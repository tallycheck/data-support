package com.taoswork.tallycheck.datasolution.mongo.core.metaaccess;

import com.taoswork.tallycheck.datadomain.onmongo.PersistableDocument;
import com.taoswork.tallycheck.datasolution.core.metaaccess.helper.EntityMetaRawAccess;
import com.taoswork.tallycheck.datasolution.core.metaaccess.impl.BaseEntityMetaAccess;
import com.taoswork.tallycheck.datasolution.mongo.config.MongoPersistableConfiguration;
import com.taoswork.tallycheck.descriptor.metadata.utils.GeneralFieldScanMethod;
import com.taoswork.tallycheck.general.solution.reflect.AnnotationUtility;
import com.taoswork.tallycheck.general.solution.reflect.FieldScanner;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Gao Yuan on 2016/2/15.
 */
public final class MongoEntityMetaAccessBase extends BaseEntityMetaAccess
        implements MongoEntityMetaAccess {

    public class MongoEntityMetaRawAccess implements EntityMetaRawAccess {
        @Override
        public Class<?>[] getAllEntities() {
            return persistableEntities;
        }

        @Override
        public Field getIdField(Class<?> entityClass) {
            GeneralFieldScanMethod fsm = new GeneralFieldScanMethod();
            fsm.setIncludeId(true).setIncludeStatic(false).setIncludeTransient(false);
            List<Field> fields = FieldScanner.getFields(entityClass, fsm);
            for (Field f : fields) {
                if (AnnotationUtility.isFieldAnnotated(f, "id")) {
                    return f;
                }
            }

            return null;
        }
    }

    @Resource(name = MongoPersistableConfiguration.PERSISTABLE_ENTITIES_BEAN_NAME)
    public Class<? extends PersistableDocument>[] persistableEntities;

    @Override
    protected Class<?> superPersistable() {
        return PersistableDocument.class;
    }

    @Override
    public EntityMetaRawAccess makeEntityMetaRawAccess() {
        return new MongoEntityMetaRawAccess();
    }
}
