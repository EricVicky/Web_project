package com.alu.omc.oam.config;

import java.util.Date;


public class COMStack
{
private COMType comType;
private String name;
private Date updatedate;
private String comConfig;
private Status status = Status.STANDALONE;
private ActionResult actionResult;

public COMStack(COMConfig config){
    this.name = config.getStackName();
    this.comType = config.getCOMType();
    this.updatedate = new Date();
    this.comConfig = config.toJson();
//    this.actionResult= config
}
public COMType getComType()
{
    return comType;
}

public COMStack(){
    
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

public String getComConfig()
{
    return comConfig;
}

public void setComConfig(String comConfig)
{
    this.comConfig = comConfig;
}
public Status getStatus() {
	return status;
}
public void setStatus(Status status) {
	this.status = status;
}
public ActionResult getActionResult() {
	return actionResult;
}
public void setActionResult(ActionResult actionResult) {
	this.actionResult = actionResult;
}

}
