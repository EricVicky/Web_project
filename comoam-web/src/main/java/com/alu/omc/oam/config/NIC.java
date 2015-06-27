package com.alu.omc.oam.config;

import java.util.ArrayList;
import java.util.List;

public class NIC
{
private String name;
private IFCfg ipv4;
private List<IFCfg>  ipv6 = new ArrayList<IFCfg>();
public String getName()
{
    return name;
}
public void setIpv6(List<IFCfg> ipv6)
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
public List<IFCfg> getIpv6()
{
    return ipv6;
}
public void addIpv6(IFCfg cfg){
    ipv6.add(cfg);
}
}
