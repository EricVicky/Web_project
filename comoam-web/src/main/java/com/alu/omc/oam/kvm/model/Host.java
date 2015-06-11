package com.alu.omc.oam.kvm.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alu.omc.oam.ansible.Entity;

  public class Host implements Entity , Serializable
    {
        /**
      * @Fields serialVersionUID :
      */
    private static final long serialVersionUID = -2647064957473634595L;
        String name;
        String ip_address;
        String user = "root";
        String password = "newsys";

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
                return StringUtils.join(new String[] { this.ip_address,
                    "ansible_ssh_user=".concat(this.user),
                    "ansible_ssh_pass=".concat(this.password).concat("\n") }, " ");
            }
        }
        
        public static boolean isLocalHost(String ip_address){
            return ip_address!=null && (ip_address.equals("127.0.0.1") || ip_address.equals("localhost"));
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