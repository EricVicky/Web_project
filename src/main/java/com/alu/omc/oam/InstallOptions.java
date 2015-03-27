package com.alu.omc.oam;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.alu.omc.oam.util.Json2Object;

public class InstallOptions
{
    private static Map  opMap  = new HashMap();
    private final static String INSTALL_OPTIONS_JSOM_FILE = "install_option_map.json";
    static
    {
        try
        {
            URI uri = InstallOptions.class.getResource(INSTALL_OPTIONS_JSOM_FILE).toURI();
            opMap = Json2Object.toMap(new File(uri));
        }
        catch (URISyntaxException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static String get(COMType comType, String vm){
        Map<String, String> comTypeOptions = (Map)opMap.get(comType);
        return comTypeOptions.get(vm);

    }

}
