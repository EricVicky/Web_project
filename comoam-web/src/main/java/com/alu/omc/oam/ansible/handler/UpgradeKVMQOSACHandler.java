package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("UPGRADE_KVM_QOSAC_HANDLER")
@Scope(value = "prototype")
public class UpgradeKVMQOSACHandler extends InstallKVMOVMHandler {

}
