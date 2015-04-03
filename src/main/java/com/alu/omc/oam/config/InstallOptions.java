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
    private final static String INSTALL_OPTIONS_JSOM_FILE = "/install_option_map.json";
    private static Logger log = LoggerFactory.getLogger(InstallOptions.class);

    public static String get(COMType comType, String vm){
    	try
        {
    		log.debug("Install Options: " + InstallOptions.class.getResource(INSTALL_OPTIONS_JSOM_FILE));
            URI uri = InstallOptions.class.getResource(INSTALL_OPTIONS_JSOM_FILE).toURI();
            opMap = Json2Object.toMap(new File(uri));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, String> comTypeOptions = (Map)opMap.get(comType.toString());
        return comTypeOptions.get(vm);

    }
    
    public static void main(String[] args) {
    	InstallOptions.get(COMType.FCAPS, "oam");
    }

}
