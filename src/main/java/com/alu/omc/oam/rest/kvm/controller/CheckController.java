package com.alu.omc.oam.rest.kvm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.ansible.AnsibleRuningContext;
import com.alu.omc.oam.ansible.Host;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.HostService;

@RestController 
public class CheckController 
{

    @Resource
    private HostService hostService;
    @Resource
    COMStackService cOMStackService;
    @Resource
    AnsibleRuningContext runningContext;

    @RequestMapping(value="/check/ping", method=RequestMethod.POST)
    public boolean  ping(@ModelAttribute("host") String host) 
    {
    	return hostService.ping((String)host);
    }
    
    @RequestMapping(value="/kvm/check/unique", method=RequestMethod.GET)
    public boolean uniqueCOM(@ModelAttribute("name") String name){
       List<COMStack> stacks =  cOMStackService.list();
       boolean unique = true;
       if (name!=null && stacks != null && stacks.size() >0)
        {
            for(COMStack stack : stacks){
               if (stack.getName().equals(name)){
                   unique = false;
                   break;
               }
            }
        }
       return unique;
    }

    @RequestMapping(value="/check/lockedhost", method=RequestMethod.GET)
    public boolean  checkHostRunning(@RequestBody Host  host) 
    {
    	return runningContext.isLocked(host);
    }
}
