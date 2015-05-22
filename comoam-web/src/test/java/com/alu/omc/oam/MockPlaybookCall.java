package com.alu.omc.oam;

import com.alu.omc.oam.ansible.Ansibleworkspace;
import com.alu.omc.oam.ansible.IPlaybookCall;

public class MockPlaybookCall implements IPlaybookCall
{

    @Override
    public String prepare(Ansibleworkspace space)
    {
        // TODO Auto-generated method stub
        return "mock";
    }

}
