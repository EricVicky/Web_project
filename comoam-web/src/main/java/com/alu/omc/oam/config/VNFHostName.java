package com.alu.omc.oam.config;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.util.Json2Object;

public class VNFHostName
{
    private static Map  opMap  = new HashMap();

    private final static String HOSTNAME_JSOM_FILE = "./config/hostname_map.json";
    private final static String HOSTNAME_KEY = "hostname";

    private static Logger log = LoggerFactory.getLogger(VNFHostName.class);

    public static String get(COMType comType, String vm){
    	try
        {
    		log.debug(VNFHostName.class.getClassLoader().getResource(HOSTNAME_JSOM_FILE) + "");
            URI uri = VNFHostName.class.getClassLoader().getResource(HOSTNAME_JSOM_FILE).toURI();
            opMap = Json2Object.toMap(new File(uri));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Map<String, String> comTypeOptions = (Map)opMap.get(comType.toString());
        return comTypeOptions.get(vm);

    }
    
    public static void add(Map vm_config, COMType comType, String vm, String deployment_prefix){
        vm_config.put(HOSTNAME_KEY, get(comType, vm));
    }
    
    public static void main(String[] args) {
    	VNFHostName.get(COMType.FCAPS, "oam");
    }

}
