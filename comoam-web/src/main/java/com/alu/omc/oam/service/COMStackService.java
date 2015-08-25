package com.alu.omc.oam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.ansible.Ansibleworkspace;
import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.config.OperationLog;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.os.conf.OpenstackConfig;

@Service
public class COMStackService
{
    @Resource
    private JsonDataSource dataSource;
    private static Logger log = LoggerFactory.getLogger(COMStackService.class);
    public void add(COMStack comStack)
    {
        List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
			return;
		}
        if(stacks == null){
            stacks = new ArrayList<COMStack>();
        }
        if(!existstack(comStack)){
        	stacks.add(comStack);
        }
        dataSource.save(stacks);
    }
    
    public boolean existstack(COMStack comStack){
    	List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
            return false;
		}
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
    	List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
			return;
		}
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
    	List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
			return;
		}
        if(stacks == null){
            stacks = new ArrayList<COMStack>();
        }
        for(COMStack stack : stacks){
            if(stack.getName().equals(comStack.getName())){
                stack.setStatus(comStack.getStatus());
                stack.setMate(comStack.getMate());
                stack.setRole(comStack.getRole());
                break;
            }
        }
        dataSource.save(stacks);
        
    }
    
    public void delete(String name){
    	if(name == null || name.length() == 0){
    		return;
    	}
    	List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
			return;
		}
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
    	List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
			return null;
		}
        return stacks;
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
    	List<COMStack> stacks=null;
		try {
			stacks = dataSource.list();
		} catch (Exception e) {
			log.error("failed to list comstack.json", e);
			return null;
		}
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
