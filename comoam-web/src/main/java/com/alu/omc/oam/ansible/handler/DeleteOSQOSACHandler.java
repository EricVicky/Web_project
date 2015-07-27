package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.QosacCOMConfig;
import com.alu.omc.oam.config.QosacOSCOMConfig;

@Component("DELETE_OPENSTACK_QOSAC_HANDLER")
@Scope(value = "prototype")
public class DeleteOSQOSACHandler extends DeleteOsHandler {
	public String getFulltopic() {
	    QosacOSCOMConfig cfg = (QosacOSCOMConfig) config;
		return this.topic.concat(cfg.getStackName());
	}
}
