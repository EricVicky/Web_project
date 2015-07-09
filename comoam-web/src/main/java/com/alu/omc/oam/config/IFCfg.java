package com.alu.omc.oam.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IFCfg implements  Serializable{
    private static final long serialVersionUID = -3992753855191713246L;
    public IFCfg()
    {
    }
    String netmask;
    String gateway;
    String ipaddress;
    String network;
    String prefix;
    public String getPrefix()
    {
        return prefix;
    }
    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }
    public String getNetmask()
    {
        return netmask;
    }
    public void setNetmask(String netmask)
    {
        this.netmask = netmask;
    }
    public String getGateway()
    {
        return gateway;
    }
    public void setGateway(String gateway)
    {
        this.gateway = gateway;
    }
    public String getIpaddress()
    {
        return ipaddress;
    }
    public void setIpaddress(String ipaddress)
    {
        this.ipaddress = ipaddress;
    }
    public String getNetwork()
    {
        return network;
    }
    public void setNetwork(String network)
    {
        this.network = network;
    }
}
