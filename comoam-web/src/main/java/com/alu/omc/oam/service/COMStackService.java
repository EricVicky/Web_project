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
        if(!existstack(comStack)){
        	stacks.add(comStack);
        }
        dataSource.save(stacks);
    }
    
    public boolean existstack(COMStack comStack){
    	List<COMStack> stacks = dataSource.list();
    	if(stacks != null && stacks.size() >0){
            for(COMStack stack : stacks){
				if (stack.getName().equals(comStack.getName())) {
					return true;
				}
            }
        }
    	return false;
    }
    
    public void update(COMStack comStack){
        List<COMStack> stacks = dataSource.list();
        if(stacks == null){
            stacks = new ArrayList<COMStack>();
        }
        for(COMStack stack : stacks){
            if(stack.getName().equals(comStack.getName())){
                stack.setUpdatedate(new Date());
                stack.setComConfig(comStack.getComConfig());
                stack.setStatus(comStack.getStatus());
                stack.setActionResult(comStack.getActionResult());
                break;
            }
        }
        dataSource.save(stacks);
        
    }
    
    public void grupdate(COMStack comStack){
        List<COMStack> stacks = dataSource.list();
        if(stacks == null){
            stacks = new ArrayList<COMStack>();
        }
        for(COMStack stack : stacks){
            if(stack.getName().equals(comStack.getName())){
                stack.setStatus(comStack.getStatus());
                break;
            }
        }
        dataSource.save(stacks);
        
    }
    
    public void delete(String name){
    	if(name == null || name.length() == 0){
    		return;
    	}
        List<COMStack> stacks = dataSource.list();
        if(stacks != null && stacks.size() >0){
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
    public COMStack get(String name)
    {
    	if(name == null || name.length() == 0){
    		return null;
    	}
        List<COMStack> stacks = dataSource.list();
        COMStack comStack=null;
        if(stacks != null && stacks.size() >0){
          for(COMStack stack : stacks){
            if(stack.getName().equals(name)){
            	comStack = stack;
            	break;
            }
          }
        }
    	return comStack;
    }
}
