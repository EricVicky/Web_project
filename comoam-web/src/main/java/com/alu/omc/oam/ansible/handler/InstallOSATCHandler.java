package com.alu.omc.oam.ansible.handler;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("INSTALL_OPENSTACK_ATC_HANDLER")
@Scope(value = "prototype")
public class InstallOSATCHandler extends InstallOSOVMHandler {


}
