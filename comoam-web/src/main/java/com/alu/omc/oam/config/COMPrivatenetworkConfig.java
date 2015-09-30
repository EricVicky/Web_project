package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.Stack;

public class COMPrivatenetworkConfig implements Serializable{
	
	private static final long serialVersionUID = -8224828541397406250L;
    String                    netmask;
    String                    gateway;
    String                    cidr;
    String                    prefix;
    Stack<String> ippool = new Stack<String>();
    
    public String getCidr()
    {
        return cidr;
    }

    public void setCidr(String cidr)
    {
        this.cidr = cidr;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    public String getGateway()
    {
        return gateway;
    }

    public void setGateway(String gateway)
    {
        this.gateway = gateway;
    }

    public String getNetmask()
    {
        return netmask;
    }

    public void setNetmask(String netmask)
    {
        this.netmask = netmask;
    }
    
    public COMPrivatenetworkConfig(){
        
    }
    
    public COMPrivatenetworkConfig( String cidr, String gateway, String netmask, String prefix){
        this.cidr = cidr;
        this.netmask = netmask;
        this.gateway = gateway;
        this.prefix = prefix;
        ippool.push("192.168.10.115");
        ippool.push("192.168.10.116");
        ippool.push("192.168.10.117");
    }
    
    public String popIp(){
        return ippool.pop();
    }
}
