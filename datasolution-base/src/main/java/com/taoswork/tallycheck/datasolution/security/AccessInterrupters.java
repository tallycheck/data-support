package com.taoswork.tallycheck.datasolution.security;

import com.taoswork.tallycheck.datadomain.base.entity.Persistable;
import com.taoswork.tallycheck.dataservice.operator.Operator;
import org.apache.commons.collections4.map.MultiValueMap;

import java.util.Collection;

/**
 * Created by gaoyuan on 7/18/16.
 */
public class AccessInterrupters {
    private final MultiValueMap<String, AccessInterrupter> typedInterrupters = new MultiValueMap<String, AccessInterrupter>();

    public void register(AccessInterrupter interrupter) {
        typedInterrupters.put(interrupter.getResource(), interrupter);
    }

    private interface CallAccessInterrupter {
        void call(AccessInterrupter interrupter);
    }

    private void probeCall(Class<? extends Persistable> projectedEntityType, CallAccessInterrupter callAccessInterrupter) {
        String type = projectedEntityType.getName();
        Collection<AccessInterrupter> interrupters = typedInterrupters.getCollection(type);
        if (interrupters != null) {
            for (AccessInterrupter itr : interrupters) {
                callAccessInterrupter.call(itr);
            }
        }
    }

    public void probeCreate(final Operator operator, Class<? extends Persistable> projectedEntityType, final Persistable entity) throws SecurityException {
        probeCall(projectedEntityType, new CallAccessInterrupter() {
            @Override
            public void call(AccessInterrupter interrupter) {
                interrupter.probeCreate(operator, entity);
            }
        });
    }

    public void probeRead(final Operator operator, Class<? extends Persistable> projectedEntityType, final Persistable entity) throws SecurityException {
        probeCall(projectedEntityType, new CallAccessInterrupter() {
            @Override
            public void call(AccessInterrupter interrupter) {
                interrupter.probeRead(operator, entity);
            }
        });
    }

    public void probeUpdate(final Operator operator, Class<? extends Persistable> projectedEntityType, final Persistable entity, final Persistable oldEntity) throws SecurityException {
        probeCall(projectedEntityType, new CallAccessInterrupter() {
            @Override
            public void call(AccessInterrupter interrupter) {
                interrupter.probeUpdate(operator, entity, oldEntity);
            }
        });
    }

    public void probeDelete(final Operator operator, Class<? extends Persistable> projectedEntityType, final Persistable entity) throws SecurityException {
        probeCall(projectedEntityType, new CallAccessInterrupter() {
            @Override
            public void call(AccessInterrupter interrupter) {
                interrupter.probeDelete(operator, entity);
            }
        });
    }
}