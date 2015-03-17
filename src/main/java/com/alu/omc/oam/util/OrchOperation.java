/* ------------------------------------------------------------------------------------------
 * Copyright (c) 2005 by Alcatel CIT. All rights reserver
 * ------------------------------------------------------------------------------------------
 * FILE                           : OrchOperation
 * ------------------------------------------------------------------------------------------
 * DESCRIPTION              : 
 * CREATION DATE            : July, 2014 
 * AUTHOR                   : Rex Duan
 * 
 * PROJECT                  : OMC-CN
 * ------------------------------------------------------------------------------------------
 * CLASS
 *
 * ------------------------------------------------------------------------------------------
 * HISTORY
 * July, 2014      Rex Duan     Creation 
 * Nov,  2014      Rex Duan       OMC-26034 COM - MANO - MRF Installation - Provisioning
 * ------------------------------------------------------------------------------------------
 */
package com.alu.omc.oam.util;

public enum OrchOperation {
    Installation("Installation", "install"), Termination("Termination", "terminate"), Growth("Growth", "grow"), Degrowth(
            "Degrowth", "degrow"), Upgrade("Upgrade", "upgrade"), Resume("Resume", "resume"), ScriptTerminate("ScriptTerminate", "sterminate");

    private String name;
    private String action;

    private OrchOperation(String name, String action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public static String getAction(String operation) {
        for (OrchOperation op : OrchOperation.values()) {
            if (op.name.equals(operation)) {
                return op.action;
            }
        }
        return null;
    }
}
