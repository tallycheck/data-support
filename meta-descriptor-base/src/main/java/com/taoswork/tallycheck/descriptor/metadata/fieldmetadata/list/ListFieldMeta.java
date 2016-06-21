package com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.list;

import com.taoswork.tallycheck.datadomain.base.presentation.FieldType;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BaseCollectionFieldMeta;
import com.taoswork.tallycheck.descriptor.metadata.fieldmetadata.BasicFieldMetaObject;

import java.util.Collection;

/**
 * Created by Gao Yuan on 2016/2/21.
 */
public abstract class ListFieldMeta<Lf extends ListFacet> extends BaseCollectionFieldMeta {
    protected final Lf facet;
    public ListFieldMeta(BasicFieldMetaObject bfmo, Lf facet) {
        super(bfmo);
        this.facet = facet;
    }

    public abstract Class getEntryClass();

    public Class getListImplementClass(){
        return facet.getListImplType();
    }

    @Override
    protected FieldType overrideUnknownFieldType() {
        return FieldType.COLLECTION;
    }

    @Override
    public int gatherReferencingTypes(Collection<Class> collection) {
        Class entryType = getPresentationClass();
        if(entryType != null){
            collection.add(entryType);
            return 1;
        }
        return 0;
    }
}
