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
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.Status;

@Component("GRINST_SEC_KVM_HANDLER")
@Scope(value = "prototype")
public class GrInstSecKVMHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrInstSecKVMHandler.class);
    @Override
    public void onStart()
    {
    	super.onStart();
    	log.info("Start Secondary COM GR installation on KVM");
    }
    @SuppressWarnings("unchecked")
    private COMConfig getMateConfig(){
        return ((GRInstallConfig<KVMCOMConfig>)config).getPri();
    }

	@Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.GRINSTALLED);
    	stack.setMate(getMateConfig().getStackName());
    	stack.setRole(GRROLE.Secondary);
        service.grupdate(stack);

        log.info("Secondary COM GR installation succeeded on KVM");
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
    	log.error("Secondary COM GR installation failed on KVM");
    }

	@Override
	public Action getAction() {
		// TODO Auto-generated method stub
		return Action.GRINST_SEC;
	}
}
