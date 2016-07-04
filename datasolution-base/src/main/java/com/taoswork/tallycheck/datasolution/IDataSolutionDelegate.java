package com.taoswork.tallycheck.datasolution;

/**
 * Created by Gao Yuan on 2015/5/10.
 */
public interface IDataSolutionDelegate {
    IDataSolutionDefinition getDataSolutionDefinition();

    IDataSolution theDataSolution();
}
