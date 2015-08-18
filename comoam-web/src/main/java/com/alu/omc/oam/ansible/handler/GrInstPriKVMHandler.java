package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.Status;
import com.alu.omc.oam.log.ParseResult;

@Component("GRINST_PRI_KVM_HANDLER")
@Scope(value = "prototype")
public class GrInstPriKVMHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrInstPriKVMHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("start Primary COM GR installation on KVM");
    }

	@Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.GRINSTALLED);
        service.grupdate(stack);
        log.info("Primary COM GR installation succeeded on KVM");
    }

    @Override
    public void onError()
    {
    	log.error("Primary COM GR installation failed on KVM");
    }

	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return Action.GRINST_PRI;
	}
}
