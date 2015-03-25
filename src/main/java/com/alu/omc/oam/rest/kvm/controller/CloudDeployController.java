package com.alu.omc.oam.rest.kvm.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.os.config.OSCOMConfig;

@RestController
public class CloudDeployController
{
    @RequestMapping(value="/kvm/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody OSCOMConfig config)
    {
        System.out.print(config.toString());
        
    }
}
