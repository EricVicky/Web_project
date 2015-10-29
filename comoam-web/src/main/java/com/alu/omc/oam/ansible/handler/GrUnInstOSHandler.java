package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.BACKUPConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.GRUnInstallConfig;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.config.Status;
import com.alu.omc.oam.log.ParseResult;

@Component("GRUNINST_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class GrUnInstOSHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrUnInstOSHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("Start uninstall GR on Openstack");
    }

    @Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.removeGR();
        service.grupdate(stack);
        log.info("uninstall GR succeeded on Openstack");
        
    }

    @Override
    public void onError()
    {
    	log.error("uninstall GR failed on Openstack");
    }
    
    @Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.GRUNINSTALL_SUCCEED;
		}else{
			return ActionResult.GRUNINSTALL_FAIL;
		}
	}

	@Override
	public Action getAction() {
		return Action.GRUNINST;
	}
}
