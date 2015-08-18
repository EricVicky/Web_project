package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.BACKUPConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.config.Status;
import com.alu.omc.oam.log.ParseResult;

@Component("GRINST_SEC_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class GrInstSecOSHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrInstSecOSHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("Start Secondary COM GR installation on Openstack");
    }

	@Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.GRINSTALLED);
        service.grupdate(stack);
        log.info("Secondary COM GR installation succeeded on Openstack");
        
    }
    @Override
    public void onError()
    {
    	log.error("Secondary COM GR installation failed on Openstack");
    }

	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return Action.GRINST_SEC;
	}
}
