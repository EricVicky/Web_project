/* ------------------------------------------------------------------------------------------
 * Copyright (c) 2005 by Alcatel CIT. All rights reserver
 * ------------------------------------------------------------------------------------------
 * FILE                           : CommandResult
 * ------------------------------------------------------------------------------------------
 * DESCRIPTION              : 
 * CREATION DATE            : July, 2014 
 * AUTHOR                   : Tristan yu
 * 
 * PROJECT                  : OMC-CN
 * ------------------------------------------------------------------------------------------
 * CLASS
 *
 * ------------------------------------------------------------------------------------------
 * HISTORY
 * July, 2014      Tristan yu     Creation 
 * ------------------------------------------------------------------------------------------
 */
package com.alu.omc.oam.util;

public class CommandResult {
    String outputString;
    int exitValue;

    public CommandResult(int exitValue, String outputString) {
        this.exitValue = exitValue;
        this.outputString = outputString;
    }

    public String getOutputString() {
        return outputString;
    }

    public int getExitValue() {
        return exitValue;
    }

    public boolean isSucceed() {
        return exitValue == 0;
    }

    public boolean isFailed() {
        return !isSucceed();
    }

    @Override
    public String toString() {
        return outputString + exitValue;
    }

}
