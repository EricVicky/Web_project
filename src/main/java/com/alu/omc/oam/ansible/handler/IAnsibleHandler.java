package com.alu.omc.oam.ansible.handler;

import com.alu.omc.oam.config.COMConfig;

public interface IAnsibleHandler
{
public void onStart();

public void onError();

public void onSucceed();

public void onEnd();

public void Parse(String log);

public void setConfig(COMConfig config);
}
