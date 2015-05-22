package com.alu.omc.oam.ansible;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.ansible.handler.IAnsibleHandler;

public interface IAnsibleInvoker
{
    public void invoke(PlaybookCall pc ,  IAnsibleHandler handler) throws AnsibleException;
    public Ansibleworkspace getWorkSpace();
}
