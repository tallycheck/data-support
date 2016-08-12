package com.taoswork.tallycheck.dataservice.exception;

import java.io.Serializable;
import java.util.*;

/**
 * Created by gaoyuan on 8/11/16.
 */
class EasyMultiValueMap<K, V> implements Serializable {
    private Map<K, List<V>> targetMap = new HashMap<K, List<V>>();

    public void add(K key, V val){
        List<V> vals = targetMap.get(key);
        if(vals == null){
            vals = new ArrayList<V>();
            targetMap.put(key, vals);
        }
        vals.add(val);
    }

    public List<V> getAll(K key){
        return targetMap.get(key);
    }

    public Collection<K> getKeys(){
        return targetMap.keySet();
    }

}
