package com.alu.omc.oam.rest.kvm.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.Action;
import com.alu.omc.oam.AnsibleInvoker;
import com.alu.omc.oam.Playbook;
import com.alu.omc.oam.PlaybookCall;
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
        Playbook playbook = PlaybookFactory.getInstance().getPlaybook(Action.INSTALL, config);
        
        
    }
    @RequestMapping(value="/kvm/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        System.out.println(config.toString());
        PlaybookCall playbookCall = new PlaybookCall(config, Action.INSTALL);
        AnsibleInvoker ansibleInvoker = new AnsibleInvoker();
        ansibleInvoker.invoke(playbookCall);
    }
}
