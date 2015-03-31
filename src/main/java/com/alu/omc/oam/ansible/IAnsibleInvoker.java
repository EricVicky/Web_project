package com.alu.omc.oam.ansible;

import com.alu.omc.oam.ansible.exception.AnsibleException;

public interface IAnsibleInvoker
{
    public void invoke(PlaybookCall pc) throws AnsibleException;
    public Ansibleworkspace getWorkSpace();
}
