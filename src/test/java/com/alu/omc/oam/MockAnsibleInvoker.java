package com.alu.omc.oam;

import javax.annotation.Resource;

import com.alu.omc.oam.ansible.Ansibleworkspace;
import com.alu.omc.oam.ansible.IAnsibleInvoker;
import com.alu.omc.oam.ansible.PlaybookCall;
import com.alu.omc.oam.ansible.exception.AnsibleException;

public class MockAnsibleInvoker implements IAnsibleInvoker
{

    @Resource
    private Ansibleworkspace ansibleworkspace;
    @Override
    public void invoke(PlaybookCall pc) throws AnsibleException
    {
              
    }
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
