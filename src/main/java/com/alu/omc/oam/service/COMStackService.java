package com.alu.omc.oam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.os.conf.OpenstackConfig;

@Service
public class COMStackService
{
    @Resource
    private JsonDataSource dataSource;

    public void add(COMStack comStack)
    {
        List<COMStack> stacks = dataSource.list();
        if(stacks == null){
            stacks = new ArrayList<COMStack>();
        }
        stacks.add(comStack);
        dataSource.save(stacks);
    }
    
    public void update(COMStack comStack){
        List<COMStack> stacks = dataSource.list();
        if(stacks == null){
            stacks = new ArrayList<COMStack>();
            for(COMStack stack : stacks){
                if(stack.getName().equals(comStack.getName())){
                    stack.setUpdatedate(new Date());
                    stack.setComConfig(comStack.getComConfig());
                    break;
                }
            }
            dataSource.save(stacks);
        }
        
    }
    public void delete(String name){
    	if(name == null || name.length() == 0){
    		return;
    	}
        List<COMStack> stacks = dataSource.list();
        if(stacks != null && stacks.size() >0){
            stacks = new ArrayList<COMStack>();
            for(COMStack stack : stacks){
            	if(stack.getName().equals(name)){
            		stacks.remove(stack);
            		break;
            	}
            }
        }
        dataSource.save(stacks);
        
    }
    
    public List<COMStack> list(){
        return dataSource.list();
    }
    
    public void addOpenstackConfig(OpenstackConfig config){
    	dataSource.saveConifg(config);
    }
    
    public OpenstackConfig getOpenstackConfig(){
    	return dataSource.getOpenstackConfig();
    }

}
