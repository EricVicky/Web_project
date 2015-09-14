package com.alu.omc.oam.config;

import java.io.Serializable;
public enum ActionResult implements Serializable{
    	NULL, INSTALL_SUCCEED, INSTALL_FAIL, UPGRADE_SUCCEED, UPGRADE_FAIL, MIGRATE_SUCCEED, MIGRATE_FAIL, CHHOSTNAME_SUCCEED, CHHOSTNAME_FAIL
    }