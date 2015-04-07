package com.alu.omc.oam.ansible.persistence;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
    private final String COM_STACK_JSON = "./comstack.json";
    private static Logger log = LoggerFactory.getLogger(JsonDataSource.class);

    public List<COMStack> list()
    {
        List<COMStack> comstacks = fromJSON(COM_STACK_JSON,
                new TypeReference<List<COMStack>>()
                {
                });
        return comstacks;
    }
    
    public void save(List<COMStack> comstacks){
       object2Json(COM_STACK_JSON, comstacks);
    }

    public static <T> T fromJSON(final String path, final TypeReference<T> type) {
         T data = null;
         try {
            data = new ObjectMapper().readValue(new File(path), type);
         } catch (Exception e) {
            // Handle the problem
         }
         return data;
      }
    
    public static void object2Json(String path, Object obj){
         ObjectMapper mapper = new ObjectMapper();
        try
        { 
            URI uri = null;
            try
            {
                uri = JsonDataSource.class.getClassLoader().getResource(path).toURI();
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
            mapper.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY);
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File(uri), obj);
        } catch (JsonGenerationException e)
        {
           e.printStackTrace();
        } catch (JsonMappingException e)
        {
           e.printStackTrace();
        } catch (IOException e)
        {
           e.printStackTrace();
        }catch(Throwable e){
            e.printStackTrace();
        }
    }
}
