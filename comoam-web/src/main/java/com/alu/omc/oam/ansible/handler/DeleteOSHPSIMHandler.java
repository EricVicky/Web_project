package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.HpsimOSCOMConfig;
import com.alu.omc.oam.config.QosacCOMConfig;
import com.alu.omc.oam.config.QosacOSCOMConfig;

@Component("DELETE_OPENSTACK_HPSIM_HANDLER")
@Scope(value = "prototype")
public class DeleteOSHPSIMHandler extends DeleteOsHandler {
	public String getFulltopic() {
		HpsimOSCOMConfig cfg = (HpsimOSCOMConfig) config;
		return this.topic.concat(cfg.getStackName());
	}
}
