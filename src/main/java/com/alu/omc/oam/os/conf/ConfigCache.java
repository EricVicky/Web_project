package com.alu.omc.oam.os.conf;

import javax.annotation.Resource;

import com.alu.omc.oam.service.COMStackService;


public class ConfigCache {

    @Resource
    COMStackService cOMStackService;
	
    private final static ConfigCache cc = new ConfigCache();

    private ConfigCache() {
    }

    public static ConfigCache getInstance() {
        return cc;
    }

    public OpenstackConfig getOSParam() {
    	return cOMStackService.getOpenstackConfig();
    }

}
