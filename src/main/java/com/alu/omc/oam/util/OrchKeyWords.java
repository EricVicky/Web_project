/* ------------------------------------------------------------------------------------------
 * Copyright (c) 2005 by Alcatel CIT. All rights reserver
 * ------------------------------------------------------------------------------------------
 * FILE                           : OrchKeyWords
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
 * ------------------------------------------------------------------------------------------
 */
package com.alu.omc.oam.util;

public enum OrchKeyWords {

    INIT("init", "init"), 
    SUCCESS("success", "success"), 
    FAILED("failed", "failed"), 
    RUNNING("running", "running"),
    NONE("none", "none"), 
    STARTTIME("start_time", "start_time"),
    ENDTIME("end_time", "end_time"), 
    OPERATION_ID("op_id", "op_id"), 
    VNF_NAME("vnf_name", "vnf_name"),
    STEP_ALIAS("step_alias", "step_alias"),
    NE_TYPE("ne_type", "ne_type"),
    NE_RELEASE("ne_release", "ne_release"), 
    FRIENDLY_NAME("friendly_name", "friendly_name"),
    OPERATION_RESULT("op_result", "op_result"),
    ACTIVE("active", "active"), 
    STEP_ACTIVE("step_active", "step_active"), 
    ACTION("action", "action"), 
    CANCEL("cancel", "The Operation has been cancelled"), 
    PAUSE("pause", "The Operation has been paused"), 
    RESUME("resume", "resume");

    private String status;
    private String description;

    private OrchKeyWords(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(String key) {
        for (OrchKeyWords op : OrchKeyWords.values()) {
            if (op.status.equals(key)) {
                return op.description;
            }
        }
        return null;
    }
}
