package com.alu.omc.oam.ansible.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.GRROLE;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.config.Status;

@Component("GRINST_PRI_OPENSTACK_HANDLER")
@Scope(value = "prototype")
public class GrInstPriOSHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrInstPriOSHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("start Primary COM GR installation on Openstack");
    }
    
    @SuppressWarnings("unchecked")
	private OSCOMConfig getOSConfig(){
    	return ((GRInstallConfig<OSCOMConfig>)config).getPri();
    }
    
    @SuppressWarnings("unchecked")
    private COMConfig getMateConfig(){
        return ((GRInstallConfig<OSCOMConfig>)config).getSec();
    }

	@Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.GRINSTALLED);
    	stack.setMate(getMateConfig().getStackName());
    	stack.setRole(GRROLE.Primary);
        service.grupdate(stack);
        log.info("Primary COM GR installation succeeded on Openstack");
    }

    public String getFulltopic(){
    	return this.topic.concat(getOSConfig().getStackName());
     }
    
    @Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.GRINSTALL_SUCCEED;
		}else{
			return ActionResult.GRINSTALL_FAIL;
		}
	}
    
    @Override
    public void onError()
    {
    	log.error("Primary COM GR installation failed on Openstack");
    }

	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return Action.GRINST_PRI;
	}
}
