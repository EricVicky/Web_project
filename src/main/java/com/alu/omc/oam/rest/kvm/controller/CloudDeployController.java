package com.alu.omc.oam.rest.kvm.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.ansible.AnsibleDelegator;
import com.alu.omc.oam.ansible.Playbook;
import com.alu.omc.oam.ansible.PlaybookFactory;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.service.HostService;

@RestController
public class CloudDeployController 
{
    @Resource
    private AnsibleDelegator ansibleDelegator;
    @Resource
    private HostService hostService;
    
    @RequestMapping(value="/os/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody OSCOMConfig config)
    {
        System.out.print(config.toString());
        Playbook playbook = PlaybookFactory.getInstance().getPlaybook(Action.INSTALL, config);
        
        
    }
    @RequestMapping(value="/kvm/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.execute(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/kvm/images", method=RequestMethod.GET)
    public List<String> images(@RequestBody String ip)  
    {
        try
        {
            return hostService.imagelist(ip,"root", "/var/images");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
