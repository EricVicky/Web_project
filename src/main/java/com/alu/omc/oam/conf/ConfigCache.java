package com.alu.omc.oam.conf;


public class ConfigCache {

    private final static ConfigCache cc = new ConfigCache();

    private ConfigCache() {
    }

    public static ConfigCache getInstance() {
        return cc;
    }

    public OpenstackConfig getOSParam() {

        OpenstackConfig osConfig = new OpenstackConfig(
                "http://135.111.74.75:5000/v2.0", "com-user1", "newsys", "COM");

        return osConfig;
    }

}
