package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alu.omc.oam.kvm.model.KvmFlavor;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
 public class VMConfig implements Serializable
    {
      
        /**
          * @Fields serialVersionUID :
          */
        private static final long serialVersionUID = 1L;
        String hostname;
        public String getHostname()
        {
            return hostname;
        }

        public void setHostname(String hostname)
        {
            this.hostname = hostname;
        }


        ArrayList<NIC> nic = new ArrayList<NIC>();
        String imgname;
        KvmFlavor flavor;

        public String getImgname()
        {
            return imgname;
        }

        public void setImgname(String imgname)
        {
            this.imgname = imgname;
        }

        public KvmFlavor getFlavor()
        {
            return flavor;
        }

        public void setFlavor(KvmFlavor flavor)
        {
            this.flavor = flavor;
        }

        public List<NIC> getNic()
        {
            return nic;
        }

        public void setNic(ArrayList<NIC> nic)
        {
            this.nic = nic;
        }


        @JsonCreator
        public VMConfig()
        {
        }

    }
