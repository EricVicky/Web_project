package com.alu.omc.oam.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NIC implements  Serializable
{
    private static final long serialVersionUID = 8478498625670279338L;
public NIC()
    {
 
    }
private String name;
private String bridge;
private IFCfg ip_v4;
private IFCfg  ip_v6 ;
public String getName()
{
    return name;
}
public void setIp_v6(IFCfg ipv6)
{
    this.ip_v6 = ipv6;
}
public void setName(String name)
{
    this.name = name;
}
public IFCfg getIp_v4()
{
    return ip_v4;
}
public void setIp_v4(IFCfg ipv4)
{
    this.ip_v4 = ipv4;
}
public IFCfg getIp_v6()
{
    return ip_v6;
}
public String getBridge()
{
    return bridge;
}
public void setBridge(String bridge)
{
    this.bridge = bridge;
}
}
