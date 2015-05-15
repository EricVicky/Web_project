package com.alu.omc.oam.log;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.ansible.handler.IAnsibleHandler;


public class Loglistener implements TailerListener
{
    
    private static Logger log = LoggerFactory.getLogger(Loglistener.class);
    IAnsibleHandler handler = null;
    @Override
    public void init(Tailer paramTailer)
    {
       log.info("init...."); 
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
      log.info("tail line=" + paramString);
      handler.Parse(paramString); 
    }

    @Override
    public void handle(Exception paramException)
    {
       log.error(paramException.getMessage(), paramException); 
    }
    
    public Loglistener(IAnsibleHandler handler){
        this.handler = handler;
    }
}
