package com.alu.omc.oam.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.config.COMStack;


import com.jcraft.jsch.*;	
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
    public List<String> achostip(){
    	if(SystemUtils.IS_OS_WINDOWS){
    		List<String> ips = new ArrayList();
    		ips.add("135.251.236.218");
    		ips.add("135.152.219.97");
    		ips.add("135.252.176.139");
    		return ips;
    	}else{
    		List<String> ips = new ArrayList();
    		ips.add("Not Windows OS!");
    		return ips;
    	}
    }
    public List<String> oamcmimages(){
    	List<String> images = new ArrayList();
    	images.add("1111.qcow2");
    	images.add("2222.qcow2");
    	images.add("3333.qcow2");
		return images;
    }
    public List<String> dbimages(){
    	List<String> images = new ArrayList();
    	images.add("4444.qcow2");
    	images.add("5555.qcow2");
    	images.add("6666.qcow2");
		return images;
    }
}
