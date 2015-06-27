package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alu.omc.oam.kvm.model.KvmFlavor;
import com.fasterxml.jackson.annotation.JsonCreator;

 public class VMConfig implements Serializable
    {
      
        /**
          * @Fields serialVersionUID :
          */
        private static final long serialVersionUID = 1L;
        String name;
        String imgname;
        KvmFlavor flavor;
        List<NIC> nic = new ArrayList<NIC>();

        @JsonCreator
        public VMConfig()
        {
        }

    }
