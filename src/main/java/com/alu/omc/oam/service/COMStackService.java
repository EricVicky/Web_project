package com.alu.omc.oam.service;

import java.util.ArrayList;
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
