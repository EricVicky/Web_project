package com.alu.omc.oam.config;

import java.io.Serializable;

public class BlockAvailZoneConfig implements Serializable{
	/**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;

    public String getZoneA()
    {
        return zoneA;
    }

    public void setZoneA(String zoneA)
    {
        this.zoneA = zoneA;
    }


    public BlockAvailZoneConfig()
    {
        // TODO Auto-generated constructor stub
    }


    private String zoneA;
}
