package com.alu.omc.oam.ansible.handler;

import org.apache.commons.exec.ExecuteResultHandler;

import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.log.ILogParser;

public interface IAnsibleHandler extends ExecuteResultHandler 
{
public final static String HANDLER_END_FIX = "_HANDLER";
public void onStart();

public void onError();

public void onSucceed();

public void onEnd();

public void Parse(String log);

public void setConfig(COMConfig config);

public void setLogParser(ILogParser logParser);
}
