package com.taoswork.tallybook.descriptor.dataio.copier.fieldcopier;

/**
 * Created by Gao Yuan on 2016/6/14.
 */
public enum CopyLevel {
    Read,   //Copy 2 level
    List,  //Copy 1 level
    Swap,   //Use the input directly as output
}
