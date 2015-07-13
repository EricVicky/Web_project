package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.HpsimCOMConfig;

@Component("DELETE_KVM_HPSIM_HANDLER")
@Scope(value = "prototype")

public class DeleteKVMHPSIMHandler extends DeleteKVMHandler {
	public String getFulltopic(){
		HpsimCOMConfig cfg = (HpsimCOMConfig)config;
	       return this.topic.concat(cfg.getStackName());
	}
}
