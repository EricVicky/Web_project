package com.alu.omc.oam.ansible;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;

@Component
public class AnsibleDelegator implements ApplicationContextAware
{
    
    private static Logger log = LoggerFactory.getLogger(AnsibleDelegator.class);
    private ApplicationContext applicationContext;

    @Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	//    @Resource
    IAnsibleInvoker ansibleInvoker;  
    public void execute(Action action, COMConfig config){
        PlaybookCall playbookCall = new PlaybookCall(config, Action.INSTALL);
        try
        {
            //for test only
            //mockAnsibleInvoker();
        	ansibleInvoker = (IAnsibleInvoker) applicationContext.getBean("ansibleInvoker");
            ansibleInvoker.invoke(playbookCall);
        }
        catch (AnsibleException e)
        {
            e.printStackTrace();
        }
       
    } 
    
    private void mockAnsibleInvoker(){
        ansibleInvoker = new IAnsibleInvoker() {
            @Override
            public void invoke(PlaybookCall pc) 
            {
              final File logFile = this.getWorkSpace().getLogFile();  
              new Thread(new Runnable(){

                @Override
                public void run()
                {
                    int i = 1;
                    while(true){
                        try
                        {
                            FileUtils.writeStringToFile(logFile, "line"+i +"\n", true);
                            i++;
                            try
                            {
                                Thread.sleep(3000L);
                            }
                            catch (InterruptedException e)
                            {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                        catch (IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                  
              }).start();
            }

            @Override
            public Ansibleworkspace getWorkSpace()
            {
                log.info("log file path=" + new File("./").getAbsolutePath());
                Ansibleworkspace workspace = new Ansibleworkspace("./", "log.txt"); 
                return workspace ;
            }
            
        };
        
    } 
}
