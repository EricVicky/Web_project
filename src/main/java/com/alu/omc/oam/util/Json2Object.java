package com.alu.omc.oam.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json2Object
{
    public static Map toMap(File json){
        Map obj = null; 
         try
        {
           obj = new ObjectMapper().readValue(json, HashMap.class);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         return obj;
    }
}
