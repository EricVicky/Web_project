package com.alu.omc.oam.ansible;

import org.apache.commons.lang3.StringUtils;

  public class Host implements Entity
    {
        String name;
        String ip_address;
        String user = "root";
        String password = "EMS_qd_n2";

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
            return StringUtils.join(new String[] { this.ip_address,
                    "ansible_ssh_user=".concat(this.user),
                    "ansible_ssh_pass=".concat(this.password).concat("\n") }, " ");
        }

    }