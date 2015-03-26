package com.alu.omc.oam.rest.kvm.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.Action;
import com.alu.omc.oam.PlaybookFactory;
import com.alu.omc.oam.os.config.KVMCOMConfig;
import com.alu.omc.oam.os.config.OSCOMConfig;

@RestController
public class CloudDeployController
{
    @RequestMapping(value="/os/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody OSCOMConfig config)
    {
        System.out.print(config.toString());
        PlaybookFactory.getInstance().getPlaybook(Action.INSTALL, config);
        
    }
    @RequestMapping(value="/kvm/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody KVMCOMConfig config)
    {
        System.out.print(config.toString());
        PlaybookFactory.getInstance().getPlaybook(Action.INSTALL, config);
        
    }
}
