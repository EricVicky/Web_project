package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.AtcCOMConfig;

@Component("DELETE_KVM_ATC_HANDLER")
@Scope(value = "prototype")

public class DeleteKVMATCHandler extends DeleteKVMHandler {
	public String getFulltopic(){
		AtcCOMConfig cfg = (AtcCOMConfig)config;
	       return this.topic.concat(cfg.getHost().getIp_address());
	}
}
