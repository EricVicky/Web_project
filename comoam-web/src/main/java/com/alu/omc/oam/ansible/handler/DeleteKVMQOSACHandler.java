package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.QosacCOMConfig;

@Component("DELETE_KVM_QOSAC_HANDLER")
@Scope(value = "prototype")
public class DeleteKVMQOSACHandler extends DeleteKVMHandler {
	public String getFulltopic() {
	    QosacCOMConfig cfg = (QosacCOMConfig) config;
		return this.topic.concat(cfg.getHost().getIp_address());
	}
}
