package com.alu.omc.oam.config;

import java.io.Serializable;

public class COMProvidernetworkConfig implements Serializable{
	 /**
     * @Fields serialVersionUID :
     */
    private static final long serialVersionUID = -8224828541397406250L;
    String                    network;
    String                    subnet;
    String                    netmask;
    String                    gateway;
    String                    dns1;
    String                    v6_subnet = "";
    String                    v6_gateway = "";
    String                    v6_prefix = "";

    public String getV6_subnet()
    {
        return v6_subnet;
    }

    public void setV6_subnet(String v6_subnet)
    {
        this.v6_subnet = v6_subnet;
    }

    public String getV6_gateway()
    {
        return v6_gateway;
    }

    public void setV6_gateway(String v6_gateway)
    {
        this.v6_gateway = v6_gateway;
    }

    public String getV6_prefix()
    {
        return v6_prefix;
    }

    public void setV6_prefix(String v6_prefix)
    {
        this.v6_prefix = v6_prefix;
    }

    public String getDns1()
    {
        return dns1;
    }

    public void setDns1(String dns1)
    {
        this.dns1 = dns1;
    }

    public String getGateway()
    {
        return gateway;
    }

    public void setGateway(String gateway)
    {
        this.gateway = gateway;
    }

    public String getNetwork()
    {
        return network;
    }

    public void setNetwork(String network)
    {
        this.network = network;
    }

    public String getSubnet()
    {
        return subnet;
    }

    public void setSubnet(String subnet)
    {
        this.subnet = subnet;
    }

    public String getNetmask()
    {
        return netmask;
    }

    public void setNetmask(String netmask)
    {
        this.netmask = netmask;
    }
}
