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
        String old_hostname;

        public String getOld_hostname() {
			return old_hostname;
		}

		public void setOld_hostname(String old_hostname) {
			this.old_hostname = old_hostname;
		}

		@JsonCreator
        public OLDVMConfig()
        {
        }

    }
