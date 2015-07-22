package com.alu.omc.oam.ansible;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;

@Component
public class AnsibleTasks
{
    final Map<String, AnsibleTask> tasks = new HashMap<String, AnsibleTask>();
    
    public void create(Action action, COMConfig config){
        tasks.put(config.getStackName(), new AnsibleTask(action, config));
    }
    
    public AnsibleTask get(String stackName){
        return tasks.get(stackName);
    }
    
    public void remove(String stackName){
        if(tasks.containsKey(stackName)){
            tasks.remove(stackName);
        }
    }
    

}
