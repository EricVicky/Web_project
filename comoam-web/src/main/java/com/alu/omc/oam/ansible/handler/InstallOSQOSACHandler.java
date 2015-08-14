package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("INSTALL_OPENSTACK_QOSAC_HANDLER")
@Scope(value = "prototype")
public class InstallOSQOSACHandler extends InstallOSOVMHandler {

}
