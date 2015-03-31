package com.alu.omc.oam.ansible;

import java.io.IOException;

public interface IAnsibleInvoker
{
    public void invoke(PlaybookCall pc) throws IOException, InterruptedException;
    public Ansibleworkspace getWorkSpace();
}
