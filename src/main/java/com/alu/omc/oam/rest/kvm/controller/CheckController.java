package com.alu.omc.oam.rest.kvm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.ansible.RunningHostLock;
import com.alu.omc.oam.ansible.validation.ValidationResult;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.kvm.model.HostStatus;
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
    RunningHostLock runningContext;

    @RequestMapping(value="/check/ping", method=RequestMethod.POST)
    public boolean  ping(@ModelAttribute("host") String host) 
    {
    	return hostService.ping((String)host);
    }

    
    @RequestMapping(value="/kvm/check/unique", method=RequestMethod.GET)
    public ValidationResult uniqueCOM(@ModelAttribute("name") String name){
       List<COMStack> stacks =  cOMStackService.list();
       ValidationResult res = new ValidationResult();
       if (name!=null && stacks != null && stacks.size() >0)
        {
            for(COMStack stack : stacks){
               if (stack.getName().equals(name)){
                   //unique = false;
            	   res.setSucceed(false);
                   break;
               }
            }
        }
       return res;
    }

    @RequestMapping(value="/check/lockedhost", method=RequestMethod.GET)
    public ValidationResult  checkHostLocked(@ModelAttribute("ip_address") String  ipaddress) 
    {
        ValidationResult res = new ValidationResult ();
        res.setSucceed(runningContext.isLocked(new Host(ipaddress)));
//        res.setSucceed(false);
        return res;
    }
    
    @RequestMapping(value="/check/host/status", method=RequestMethod.GET)
    public HostStatus  checkHostStatus(@ModelAttribute("ip_address") String  ipaddress) 
    {
        return runningContext.getAction(new Host(ipaddress));
        //return Action.INSTALL;
    }

}
