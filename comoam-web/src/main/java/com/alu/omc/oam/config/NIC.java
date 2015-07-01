package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NIC implements  Serializable
{
    private static final long serialVersionUID = 8478498625670279338L;
public NIC()
    {
 
    }
private String name;
private String bridge;
private IFCfg ipv4;
private IFCfg  ipv6 ;
public String getName()
{
    return name;
}
public void setIpv6(IFCfg ipv6)
{
    this.ipv6 = ipv6;
}
public void setName(String name)
{
    this.name = name;
}
public IFCfg getIpv4()
{
    return ipv4;
}
public void setIpv4(IFCfg ipv4)
{
    this.ipv4 = ipv4;
}
public IFCfg getIpv6()
{
    return ipv6;
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
