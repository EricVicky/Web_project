package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("INSTALL_KVM_HPSIM_HANDLER")
@Scope(value = "prototype")
public class InstallKVMHPSIMHandler extends InstallKVMOVMHandler {

}
