package com.alu.omc.oam.ansible.handler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.CHKVMQosacCOMConfig;
import com.alu.omc.oam.config.CHOSQosacCOMConfig;
import com.alu.omc.oam.config.COMStack;


@Component("CHHOSTNAME_KVM_QOSAC_HANDLER")
@Scope(value = "prototype")
public class CHQOSACHostnameHandler extends DefaultHandler 
{
    private static Logger log = LoggerFactory.getLogger(CHQOSACHostnameHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("upgrade start on KVM");
    }

    @Override
    public void onSucceed()
    {
       log.info("upgrade on KVM succeed");
       ((CHKVMQosacCOMConfig)config).setOld_vm_config(null);
        COMStack stack = new COMStack(config);
        service.update(stack);
        
    }
    @Override
    public void onError()
    {
        log.error("upgrade on KVM failed");
    }
    
    
    @Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.CHHOSTNAME_SUCCEED;
		}else{
			return ActionResult.CHHOSTNAME_FAIL;
		}
	}

	@Override
	public Action getAction() {
		return Action.CHHOSTNAME;
	}
}
