package com.alu.omc.oam;

import java.io.IOException;

import javax.annotation.Resource;

public class MockAnsibleInvoker implements IAnsibleInvoker
{

    @Resource
    private Ansibleworkspace ansibleworkspace;
    @Override
    public void invoke(PlaybookCall pc) throws IOException,
            InterruptedException
    {
              
    }
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
