package com.taoswork.tallycheck.info.descriptor.base;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Gao Yuan on 2015/6/25.
 */
public interface NamedOrdered extends Named {

    int getOrder();

    void setOrder(int order);

    public static class NameSorter {

        public static List<String> makeNamesOrdered(Collection<? extends NamedOrdered> infos) {
            Set<OrderedName> onSet = new TreeSet<OrderedName>(OrderedName.COMPARATOR);
            for (NamedOrdered info : infos) {
                if (info != null) {
                    onSet.add(new OrderedName(info.getName(), info.getOrder()));
                }
            }

            List<String> orderedNames = new ArrayList<String>();
            for (OrderedName orderedName : onSet) {
                orderedNames.add(orderedName.getName());
            }
            return orderedNames;
        }

        public static List<String> makeNamesOrdered(Collection<String> names, Map<String, ? extends NamedOrdered> infoMap) {
            List<NamedOrdered> infos = new ArrayList<NamedOrdered>();
            for (String name : names) {
                NamedOrdered info = infoMap.get(name);
                if (info != null) {
                    infos.add(info);
                }
            }
            return makeNamesOrdered(infos);
        }

        public static <T extends NamedOrdered> List<T> makeObjectOrdered(Collection<T> objects) {
            Map<OrderedName, T> orderedNameSet = new TreeMap<OrderedName, T>(OrderedName.COMPARATOR);
            for (T info : objects) {
                if (info != null) {
                    orderedNameSet.put(new OrderedName(info.getName(), info.getOrder()), info);
                }
            }

            List<T> orderedObjects = new ArrayList<T>();
            for (Map.Entry<OrderedName, T> entry : orderedNameSet.entrySet()) {
                orderedObjects.add(entry.getValue());
            }
            return orderedObjects;
        }

    }

}
