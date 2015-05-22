package com.alu.omc.oam;

import javax.annotation.Resource;

import com.alu.omc.oam.ansible.Ansibleworkspace;
import com.alu.omc.oam.ansible.IAnsibleInvoker;
import com.alu.omc.oam.ansible.PlaybookCall;
import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.ansible.handler.IAnsibleHandler;

public class MockAnsibleInvoker implements IAnsibleInvoker
{

    @Resource
    private Ansibleworkspace ansibleworkspace;

    @Override
    public Ansibleworkspace getWorkSpace()
    {
        return null;
    }
    @Override
    public void invoke(PlaybookCall pc, IAnsibleHandler handler)
            throws AnsibleException
    {
        
    }

}
