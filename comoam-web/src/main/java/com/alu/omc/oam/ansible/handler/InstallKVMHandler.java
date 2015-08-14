package com.alu.omc.oam.ansible.handler;

import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.exec.ExecuteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.RunningComstackLock;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionResult;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.KVMCOMConfig;


@Component("INSTALL_KVM_HANDLER")
@Scope(value = "prototype")
public class InstallKVMHandler extends DefaultHandler 
{
  
    private Pattern stackPattern = Pattern.compile("^.*TASK\\:\\s\\[vnf\\_create\\_vms\\s\\|\\screate\\svirtual\\smachine\\sinstance\\].*$");
    private static Logger log = LoggerFactory.getLogger(InstallKVMHandler.class);
    @Override
    public void onStart()
    {
    	log.info("deployment on KVM start");
        super.onStart();
    }

    @Override
    public void onError()
    {
        log.error("deployment on KVM failed");
    }

    @Override
    public void onSucceed()
    {
        log.info("deployment on KVM succeed");
    }
    @Override
	public ActionResult getActionResult(){
		if(this.succeed){
			return ActionResult.INSTALL_SUCCEED;
		}else{
			return ActionResult.INSTALL_FAIL;
		}
	}
    
    @Override
    public void Parse(String log)
    {
      if(hasError(log)){
    	  this.succeed  = false;
      }
      if((stackPattern.matcher(log)).find()){
    	  COMStack stack = new COMStack(config);
          service.add(stack);
      }
      sender.send(getFulltopic(), logParser.parse(log));
    }
    
    private boolean hasError(String log){
    	return false;
    }

	@Override
	public Action getAction() {
		return Action.INSTALL;
	}

}
