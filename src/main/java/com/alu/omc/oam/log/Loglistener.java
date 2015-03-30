package com.alu.omc.oam.log;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;

import com.alu.omc.oam.service.WebsocketSender;


public class Loglistener implements TailerListener
{
    WebsocketSender sender;
    String topic = "log";
    
    @Override
    public void init(Tailer paramTailer)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void fileNotFound()
    {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        
    }
    
    public Loglistener(WebsocketSender sender){
        this.sender = sender;
    }
}
