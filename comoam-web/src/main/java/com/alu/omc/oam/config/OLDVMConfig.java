package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alu.omc.oam.kvm.model.KvmFlavor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
 public class OLDVMConfig implements Serializable
    {
      
        /**
          * @Fields serialVersionUID :
          */
        private static final long serialVersionUID = 1L;
        String hostname;



		public String getHostname() {
			return hostname;
		}



		public void setHostname(String hostname) {
			this.hostname = hostname;
		}



		@JsonCreator
        public OLDVMConfig()
        {
        }

    }
