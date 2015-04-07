package com.alu.omc.oam.config;

import java.util.Date;


public class COMStack
{
private COMType comType;
private String name;
private Date updatedate;
private COMConfig comConfig;

public COMStack(COMConfig config){
    this.name = config.getStackName();
    this.comType = config.getCOMType();
    this.updatedate = new Date();
    this.comConfig = config;
}
public COMType getComType()
{
    return comType;
}

public void setComType(COMType comType)
{
    this.comType = comType;
}



public String getName()
{
    return name;
}

public void setName(String name)
{
    this.name = name;
}

public Date getUpdatedate()
{
    return updatedate;
}

public void setUpdatedate(Date updatedate)
{
    this.updatedate = updatedate;
}

public COMConfig getComConfig()
{
    return comConfig;
}

public void setComConfig(COMConfig comConfig)
{
    this.comConfig = comConfig;
}



}
