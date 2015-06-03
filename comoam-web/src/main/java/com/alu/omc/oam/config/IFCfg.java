package com.alu.omc.oam.config;


public class IFCfg{
    String netmask;
    String gateway;
    String ipaddress;
    String network;
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
