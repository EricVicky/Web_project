package com.alu.omc.oam;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.input.Tailer;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.log.Loglistener;
import com.alu.omc.oam.service.WebsocketSender;

@Component
public class AnsibleDelegator
{
    @Resource
    WebsocketSender websocketSender;
    @Resource
    AnsibleInvoker ansibleInvoker;

    public void execute(Action action, COMConfig config){
        PlaybookCall playbookCall = new PlaybookCall(config, Action.INSTALL);
        Tailer.create(ansibleInvoker.getLogFile(), new Loglistener(websocketSender), 5000);
        try
        {
            ansibleInvoker.invoke(playbookCall);
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
}
