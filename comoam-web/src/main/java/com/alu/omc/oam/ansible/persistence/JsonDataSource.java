package com.alu.omc.oam.ansible.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.OperationLog;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.os.conf.OpenstackConfig;
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
	
    @Value("${ansible.comstacks}")
    private  String COM_STACK_JSON;
	@Value("${ansible.operation_log}")
    private  String COM_OPERATION_JSON; 
    @Value("${ansible.hosts}")
    private  String HOSTS_JSON; 
    @Value("${ansible.openstack_config}")
    private String OPENSTACK_CONFIG_JSON;
    
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
    
    public OpenstackConfig getOpenstackConfig(){
    	OpenstackConfig config = null;
    	
    	try{
    		config = fromJSON(OPENSTACK_CONFIG_JSON, new TypeReference<OpenstackConfig>() {});
    	}catch (IOException e){
    		e.printStackTrace();
    	}
    	
    	if(config == null){
    		config = new OpenstackConfig();
    	}
    	
    	return config;
    }
    
    /*COM Operation*/
    public Map<String, List<OperationLog>> loadAllLog(){
    	Map<String, List<OperationLog>> operationlog = null;
        try
        {
        	operationlog = fromJSON(COM_OPERATION_JSON,new TypeReference<Map<String, List<OperationLog>>>(){});
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        if(operationlog == null){
        	operationlog = new HashMap<String, List<OperationLog>>();
        }
        return operationlog;
    }
 
    public void saveop(Map<String, List<OperationLog>> opLog){
        object2Json(COM_OPERATION_JSON, opLog);
    }
      
    public void save(List<COMStack> comstacks){
       object2Json(COM_STACK_JSON, comstacks);
    }
    
    public void saveConifg(OpenstackConfig openstackConfig){
    	object2Json(OPENSTACK_CONFIG_JSON, openstackConfig);
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
