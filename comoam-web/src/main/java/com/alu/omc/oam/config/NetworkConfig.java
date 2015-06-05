package com.alu.omc.oam.config;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface NetworkConfig
{
    /**
      * allInterface(list network interface configuration of each vm)
      *
      * @Title: allInterface
      * @Description: TODO
      * @param @return    
      * @return Map<String,NIC>    
      * @throws
      */
    @JsonIgnore 
    public abstract Map<String, List<NIC>> allInterface();
}
