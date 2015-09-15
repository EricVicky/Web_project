package com.alu.omc.oam.rest.kvm.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.ansible.RunningComstackLock;
import com.alu.omc.oam.ansible.validation.ValidationResult;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.Status;
import com.alu.omc.oam.kvm.model.StackStatus;
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
    RunningComstackLock runningComstackLock;

    @RequestMapping(value="/check/ping", method=RequestMethod.GET)
    public ValidationResult  ping(@ModelAttribute("host") String host) 
    {
    	ValidationResult res = new ValidationResult();
    	if (host!=null)
    	{
    		res.setSucceed(!hostService.ping((String)host));
    		if (!res.isSucceed()){
    			res.setMessage("This IP is in use, please change!");
    		}	   		
    	}
    	return res;    			
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
            	   res.setSucceed(true);
                   break;
               }
            }
        }
       return res;
    }
    
    @RequestMapping(value="/gr/kvm/checkinstalled", method=RequestMethod.GET)
    public ValidationResult GRCheck(@ModelAttribute("name") String name){
       List<COMStack> stacks =  cOMStackService.list();
       ValidationResult res = new ValidationResult();
       if (name!=null && stacks != null && stacks.size() >0)
        {
            for(COMStack stack : stacks){
               if (stack.getName().equals(name)){
                   if(stack.getStatus()==Status.GRINSTALLED){
                	   res.setSucceed(true);
                       break;
                   }else{
                	   res.setSucceed(false);
                       break;
                   }
               }
            }
        }
       return res;
    }
    
    @RequestMapping(value="/os/check/unique", method=RequestMethod.GET)
    public ValidationResult uniqueSTACK(@ModelAttribute("name") String name){
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
    public ValidationResult  checkHostLocked(@ModelAttribute("stackName") String  stackName) 
    {
        ValidationResult res = new ValidationResult ();
        res.setSucceed(runningComstackLock.islocked(stackName));
        return res;
    }
    
    @RequestMapping(value="/check/host/status", method=RequestMethod.GET)
    public StackStatus  checkHostStatus(@ModelAttribute("stackName") String  stackName) 
    {
        return runningComstackLock.getStatus(stackName);
    }


}
