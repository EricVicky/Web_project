package com.alu.omc.oam.ansible.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.Host;
import com.alu.omc.oam.config.COMStack;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
@Component
public class JsonDataSource
{

    @Value("${ansible.datasource}")
    private  String COM_STACK_JSON; 
    @Value("${ansible.hosts}")
    private  String HOSTS_JSON; 
    private static Logger log = LoggerFactory.getLogger(JsonDataSource.class);

    public List<COMStack> list()
    {
        List<COMStack> comstacks = null;
        try
        {
            comstacks = fromJSON(COM_STACK_JSON,
                    new TypeReference<List<COMStack>>()
                    {
                    });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(comstacks == null){
            comstacks = new ArrayList<COMStack>();
        }
        return comstacks;
    }
    
    public List<Host> hosts(){
        List<Host> hosts = null;
        try
        {
            hosts = fromJSON(HOSTS_JSON, new TypeReference<List<Host>>() {});
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(hosts == null){
           hosts = new ArrayList<Host>(); 
        }
        return hosts;
    }
    
    public void save(List<COMStack> comstacks){
       object2Json(COM_STACK_JSON, comstacks);
    }

    public static <T> T fromJSON(final String path, final TypeReference<T> type) throws IOException {
         T data = null;
         File f = new File(path);
         log.info("json file path=" + f.getAbsolutePath());
         if(!f.exists()){
             log.info("create json file:" + f.getAbsolutePath());
             f.createNewFile();
         }
         if(f.length()==0){
             return null;
         }
         try {
            data = new ObjectMapper().readValue(f, type);
         } catch (Exception e) {
           log.error("failed to load json file", e); 
         }
         return data;
      }
    
    public static void object2Json(String path, Object obj)
    {
        ObjectMapper mapper = new ObjectMapper();
        try
        {
            mapper.setVisibility(PropertyAccessor.SETTER,
                    Visibility.PUBLIC_ONLY);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(path), obj);
        }
        catch (JsonGenerationException e)
        {
            e.printStackTrace();
        }
        catch (JsonMappingException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
}
