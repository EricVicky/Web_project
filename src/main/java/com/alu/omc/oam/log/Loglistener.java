package com.alu.omc.oam.log;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.service.WebsocketSender;


public class Loglistener implements TailerListener
{
    WebsocketSender sender;
    String topic = "/log/tail";
    
    private static Logger log = LoggerFactory.getLogger(Loglistener.class);
    @Override
    public void init(Tailer paramTailer)
    {
       log.info("init....");; 
    }

    @Override
    public void fileNotFound()
    {
       log.error("file not found"); 
    }

    @Override
    public void fileRotated()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void handle(String paramString)
    {
       sender.send(topic, paramString); 
    }

    @Override
    public void handle(Exception paramException)
    {
       log.error(paramException.getMessage()); 
    }
    
    public Loglistener(WebsocketSender sender){
        this.sender = sender;
    }
}
