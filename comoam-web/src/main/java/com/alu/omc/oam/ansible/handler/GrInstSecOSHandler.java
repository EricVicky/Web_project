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
    	runningComstackLock.lock(getOSConfig().getStackName(), Action.GRINST_SEC);
    }
    
    @SuppressWarnings("unchecked")
	private OSCOMConfig getOSConfig(){
    	return ((GRInstallConfig<OSCOMConfig>)config).getSec();
    }

	@Override
    public void onSucceed()
    {
    	COMStack stack = new COMStack(config);
    	stack.setStatus(Status.GRINSTALLED);
        service.grupdate(stack);
        runningComstackLock.unlock(getOSConfig().getStackName());
        
    }

    @Override
    public void onEnd()
    {
    	if(this.succeed){
        	this.onSucceed();
        	END.setResult(ParseResult.SUCCEED);
        }else{
        	END.setResult(ParseResult.FAILED);
            this.onError();
        }
        sender.send(getFulltopic(), END);
        runningComstackLock.unlock(getOSConfig().getStackName());

    }
    public String getFulltopic(){
    	return this.topic.concat(getOSConfig().getStackName());
     }
    @Override
    public void onError()
    {
        runningComstackLock.unlock(getOSConfig().getStackName());
    }
}
