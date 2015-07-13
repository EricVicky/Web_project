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
import com.alu.omc.oam.config.Status;

@Component("GRINST_SEC_KVM_HANDLER")
@Scope(value = "prototype")
public class GrInstSecKVMHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrInstSecKVMHandler.class);
    @Override
    public void onStart()
    {
    	runningComstackLock.lock(getKVMConfig().getStackName(), Action.GRINST_SEC);
    }
    
    @SuppressWarnings("unchecked")
	private KVMCOMConfig getKVMConfig(){
    	return ((GRInstallConfig<KVMCOMConfig>)config).getSec();
    }

	@Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.GRINSTALLED);
        service.grupdate(stack);
        runningComstackLock.unlock(getKVMConfig().getStackName());
        
    }

    @Override
    public void onEnd()
    {
        if(this.succeed){
        	this.onSucceed();
        	sender.send(getFulltopic(), END);
        }
        runningComstackLock.unlock(getKVMConfig().getStackName());

    }
    public String getFulltopic(){
    	return this.topic.concat(getKVMConfig().getHost().getIp_address());
     }
    @Override
    public void onError()
    {
        runningComstackLock.unlock(getKVMConfig().getStackName());
    }
}
