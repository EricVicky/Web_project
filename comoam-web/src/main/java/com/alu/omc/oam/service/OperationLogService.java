package com.alu.omc.oam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.ansible.Ansibleworkspace;
import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.config.LogStatus;
import com.alu.omc.oam.config.OperationLog;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.os.conf.OpenstackConfig;

@Service
public class OperationLogService {
	
	@Resource
    private JsonDataSource dataSource;
	
	public void addLog(String stackName, OperationLog log){
    	Map<String, List<OperationLog>>  allLogs = dataSource.loadAllLog();
    	if(allLogs.get(stackName)!=null){
    		List<OperationLog> logs = allLogs.get(stackName);
    		logs.add(log);
    	}else{
    		List<OperationLog> logs = new ArrayList<OperationLog>();
    		logs.add(log);
    		allLogs.put(stackName, logs);
    	}
    	dataSource.saveop(allLogs);
    }
    
    public void deleteLog(String stackName){
    	Map<String, List<OperationLog>>  allLogs = dataSource.loadAllLog();
    	if(allLogs.get(stackName)!=null){
    		allLogs.remove(stackName);
    	}
    	dataSource.saveop(allLogs);
    }
    
    public void setLogStatus(String name,LogStatus status){
    	Map<String, List<OperationLog>>  allLogs = dataSource.loadAllLog();
    	List<OperationLog> logs = allLogs.get(name);
    	OperationLog operationLog = logs.get(logs.size()-1);
    	operationLog.setLogStatus(status);
    	dataSource.saveop(allLogs);
    }
    
}
