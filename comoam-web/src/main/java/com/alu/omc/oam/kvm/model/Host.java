package com.alu.omc.oam.kvm.model;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.ansible.Entity;
import com.alu.omc.oam.service.WebsocketSender;

  public class Host implements Entity , Serializable
    {
        /**
      * @Fields serialVersionUID :
      */
    private static final long serialVersionUID = -2647064957473634595L;
    private static Logger log = LoggerFactory.getLogger(Host.class);
        String name;
        String ip_address;
        String user = "";
        String password = "";

        public String getName()
        {
            return name;
        }
        
        public Host(String user, String password, String ip_address){
            this.user = user;
            this.password = password;
            this.ip_address = ip_address;
                    
        }
        public Host( String ip_address){
            this.ip_address = ip_address;
                    
        }
        
        public Host(){
            
        }
        public void setName(String name)
        {
            this.name = name;
        }

        public String getIp_address()
        {
            return ip_address;
        }

        public void setIp_address(String ip_address)
        {
            this.ip_address = ip_address;
        }

        public String getUser()
        {
            return user;
        }

        public void setUser(String user)
        {
            this.user = user;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
        
        public String toString()
        {
            if(isLocalHost(this.ip_address)){
                return localHost();
            }else{
                return StringUtils.join(new String[] { this.ip_address }, " ");
            }
        }
        
        public static boolean isLocalHost(String ip_address){
        	
        	log.info("check local address:" + ip_address);
        	if (ip_address == null)
        		return false;
        	
        	if (ip_address.equals("127.0.0.1") || ip_address.equals("localhost"))
        		return true;
			try {
				Enumeration e = NetworkInterface.getNetworkInterfaces();
	        	while(e.hasMoreElements())
	        	{
	        	    NetworkInterface n = (NetworkInterface) e.nextElement();
	        	    Enumeration ee = n.getInetAddresses();
	        	    while (ee.hasMoreElements())
	        	    {
	        	        InetAddress i = (InetAddress) ee.nextElement();
	        	        if(i.getHostAddress().equals(ip_address)){
					    	  log.info("localhost ip is " + ip_address);
	                          return true;
	        	        }
	        	    }
	        	}
			} catch (Exception e1) {
				log.error("failed to get localhost ip" + ip_address, e1);
				return false;
			}
        
            return false;
        }
        
        private String localHost(){
            return "localhost   ansible_connection=local".concat("\n");
        }
        
        public int hashCode(){
            return this.ip_address.hashCode();
        }
        
        public boolean equals(Object obj){
            if(obj != null && obj instanceof Host && ((Host)obj).getIp_address() != null){
                return this.ip_address.equals(((Host)obj).getIp_address()); 
            }
            return false;
        }

    }