package com.taoswork.tallycheck.authority.provider.onnothing.provider;

import com.taoswork.tallycheck.authority.provider.onnothing.Mockuper;

/**
 * Created by gaoyuan on 7/3/16.
 */
public class TypedDocRepo {
    public static final int docCount = 11;
    public final String resource;

    public final GuardedDocInstance docG;
    public final GuardedDocInstance docA;
    public final GuardedDocInstance docC;
    public final GuardedDocInstance docAB;
    public final GuardedDocInstance docAC;
    public final GuardedDocInstance docCD;
    public final GuardedDocInstance docABC;
    public final GuardedDocInstance docACD;
    public final GuardedDocInstance docABCD;
    public final GuardedDocInstance docE;
    public final GuardedDocInstance docABCDE;

    public TypedDocRepo(String resource) {
        this.resource = resource;

        docG = Mockuper.docWith(false, false, false, false, false);
        docA = Mockuper.docWith(true, false, false, false, false);
        docC = Mockuper.docWith(false, false, true, false, false);
        docAB = Mockuper.docWith(true, true, false, false, false);
        docAC = Mockuper.docWith(true, false, true, false, false);
        docCD = Mockuper.docWith(false, false, true, true, false);
        docABC = Mockuper.docWith(true, true, true, false, false);
        docACD = Mockuper.docWith(true, false, true, true, false);
        docABCD = Mockuper.docWith(true, true, true, true, false);
        docE = Mockuper.docWith(false, false, false, false, true);
        docABCDE = Mockuper.docWith(true, true, true, true, true);
    }

    public void pushAllInto(DocRepo repo){
        repo.pushIn(resource,  docG.getDomainObject());
        repo.pushIn(resource,  docA.getDomainObject());
        repo.pushIn(resource,  docC.getDomainObject());
        repo.pushIn(resource,  docAB.getDomainObject());
        repo.pushIn(resource,  docAC.getDomainObject());
        repo.pushIn(resource,  docCD.getDomainObject());
        repo.pushIn(resource,  docABC.getDomainObject());
        repo.pushIn(resource,  docACD.getDomainObject());
        repo.pushIn(resource,  docABCD.getDomainObject());
        repo.pushIn(resource,  docE.getDomainObject());
        repo.pushIn(resource,  docABCDE.getDomainObject());
    }
}
