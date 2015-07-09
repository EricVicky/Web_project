package com.alu.omc.oam.config;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.util.Json2Object;

public class InstallOptions
{
    private static Map  opMap  = new HashMap();

    private final static String INSTALL_OPTIONS_JSOM_FILE = "./config/install_option_map.json";
    private final static String INSTALL_OPTION_KEY = "install_options";

    private static Logger log = LoggerFactory.getLogger(InstallOptions.class);

    public static String get(COMType comType, String vm){
    	try
        {
    		log.debug(InstallOptions.class.getClassLoader().getResource(INSTALL_OPTIONS_JSOM_FILE) + "");
            URI uri = InstallOptions.class.getClassLoader().getResource(INSTALL_OPTIONS_JSOM_FILE).toURI();
            opMap = Json2Object.toMap(new File(uri));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Map<String, String> comTypeOptions = (Map)opMap.get(comType.toString());
        return comTypeOptions.get(vm);

    }
    
    public static void add(Map vm_config, COMType comType, String vm ){
        //vm_config.put(INSTALL_OPTION_KEY, get(comType, vm));
    }
     

    
    public static void main(String[] args) {
    	InstallOptions.get(COMType.FCAPS, "oam");
    }

}
