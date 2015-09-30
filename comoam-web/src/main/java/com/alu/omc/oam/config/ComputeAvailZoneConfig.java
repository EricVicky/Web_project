package com.alu.omc.oam.config;

import java.io.Serializable;

public class ComputeAvailZoneConfig implements Serializable{
	/**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = 1L;
    private String            zoneA;

    public String getZoneA()
    {
        return zoneA;
    }

    public void setZoneA(String zoneA)
    {
        this.zoneA = zoneA;
    }



    public ComputeAvailZoneConfig()
    {
    }
}
