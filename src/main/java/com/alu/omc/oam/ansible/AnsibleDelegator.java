package com.alu.omc.oam.ansible;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.input.Tailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.log.Loglistener;
import com.alu.omc.oam.service.WebsocketSender;

@Component
public class AnsibleDelegator
{
    
    private static Logger log = LoggerFactory.getLogger(AnsibleDelegator.class);
    @Resource
    WebsocketSender websocketSender;
    @Resource
    IAnsibleInvoker ansibleInvoker; 
    public void execute(Action action, COMConfig config){
        PlaybookCall playbookCall = new PlaybookCall(config, Action.INSTALL);
        try
        {
            //for test only
           // mockAnsibleInvoker();
            ansibleInvoker.invoke(playbookCall);
            Tailer.create(ansibleInvoker.getWorkSpace().getLogFile(), new Loglistener(websocketSender), 1000, true);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    } 
    
    private void mockAnsibleInvoker(){
        ansibleInvoker = new IAnsibleInvoker() {
            @Override
            public void invoke(PlaybookCall pc) throws IOException,
                    InterruptedException
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
                System.out.println("log file path=" + new File("./").getAbsolutePath());
                Ansibleworkspace workspace = new Ansibleworkspace("./"); 
                return workspace ;
            }
            
        };
        
    } 
}