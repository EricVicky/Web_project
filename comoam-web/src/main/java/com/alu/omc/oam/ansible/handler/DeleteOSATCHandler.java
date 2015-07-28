package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.AtcOSCOMConfig;
import com.alu.omc.oam.config.QosacCOMConfig;
import com.alu.omc.oam.config.QosacOSCOMConfig;

@Component("DELETE_OPENSTACK_ATC_HANDLER")
@Scope(value = "prototype")
public class DeleteOSATCHandler extends DeleteOsHandler {
	public String getFulltopic() {
		AtcOSCOMConfig cfg = (AtcOSCOMConfig) config;
		return this.topic.concat(cfg.getStackName());
	}
}
