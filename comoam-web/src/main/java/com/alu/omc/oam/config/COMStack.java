package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class COMStack implements  Serializable
{
private static final long serialVersionUID = 4597002583194942399L;
private COMType comType;
private String name;
private Date updatedate;
private String comConfig;
private Status status = Status.STANDALONE;
private ActionResult actionResult;
private GRROLE  role;

public COMStack(COMConfig config){
    this.name = config.getStackName();
    this.comType = config.getCOMType();
    this.updatedate = new Date();
    this.comConfig = config.toJson();
}

public GRROLE getRole()
{
    return role;
}

public void setRole(GRROLE role)
{
    this.role = role;
}

public String getMate()
{
    return mate;
}

public void setMate(String mate)
{
    this.mate = mate;
}
private String mate;


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

public void removeGR(){
    this.status = Status.STANDALONE;
    this.mate = null;
    this.role = null;
}

}
