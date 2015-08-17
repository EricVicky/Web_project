package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.BACKUPConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.GRUnInstallConfig;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.Status;
import com.alu.omc.oam.log.ParseResult;

@Component("GRUNINST_KVM_HANDLER")
@Scope(value = "prototype")
public class GrUnInstKVMHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrUnInstKVMHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("Start uninstall GR on KVM");
    }

    @Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.STANDALONE);
        service.grupdate(stack);
        log.info("uninstall GR succeeded on KVM");
        
    }

    @Override
    public void onError()
    {
    	log.error("uninstall GR failed on KVM");
    }

	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return Action.GRUNINST;
	}
}
