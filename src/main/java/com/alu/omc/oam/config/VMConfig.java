package com.alu.omc.oam.config;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

 public class VMConfig implements Serializable
    {
      
        /**
          * @Fields serialVersionUID :
          */
        private static final long serialVersionUID = 1L;
         String                    provider_ip_address;
         String                    private_ip_address;
         String                    flavor;
        String                    image;
        int                       com_data_vol_size;
        
        @JsonProperty 
        public String getProvider_ip_address()
        {
            return provider_ip_address;
        }

        public void setProvider_ip_address(String provider_ip_address)
        {
            this.provider_ip_address = provider_ip_address;
        }
        @JsonProperty  
        public String getPrivate_ip_address()
        {
            return private_ip_address;
        }

        public void setPrivate_ip_address(String private_ip_address)
        {
            this.private_ip_address = private_ip_address;
        }
        @JsonProperty 
        public String getFlavor()
        {
            return flavor;
        }

        public void setFlavor(String flavor)
        {
            this.flavor = flavor;
        }
         @JsonProperty 
        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }
         @JsonProperty 
        public int getCom_data_vol_size()
        {
            return com_data_vol_size;
        }

        public void setCom_data_vol_size(int com_data_vol_size)
        {
            this.com_data_vol_size = com_data_vol_size;
        }
        @JsonCreator
        public VMConfig()
        {
        }

    }
