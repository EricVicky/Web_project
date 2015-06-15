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

@Component("GRUNINST_KVM_HANDLER")
@Scope(value = "prototype")
public class GrUnInstKVMHandler extends DefaultHandler{
	
	private static Logger log = LoggerFactory.getLogger(GrUnInstKVMHandler.class);
    @Override
    public void onStart()
    {
    	runningContext.lock(getKVMConfig().getHost(), Action.GRINST_PRI);
    }
    
    @SuppressWarnings("unchecked")
	private KVMCOMConfig getKVMConfig(){
    	return ((GRUnInstallConfig<KVMCOMConfig>)config).getComConfig();
    }

    @Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.STANDALONE);
        service.grupdate(stack);
        runningContext.unlock(getKVMConfig().getHost());
        
    }

    @Override
    public void onEnd()
    {
        if(this.succeed){
        	this.onSucceed();
        	sender.send(getFulltopic(), END);
        }
        runningContext.unlock(getKVMConfig().getHost());

    }
    public String getFulltopic(){
    	return this.topic.concat(getKVMConfig().getHost().getIp_address());
     }
    @Override
    public void onError()
    {
        runningContext.unlock(getKVMConfig().getHost());
    }
}
