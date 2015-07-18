package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("INSTALL_KVM_ARS_HANDLER")
@Scope(value = "prototype")
public class InstallKVMARSHandler extends InstallKVMOVMHandler {

}
