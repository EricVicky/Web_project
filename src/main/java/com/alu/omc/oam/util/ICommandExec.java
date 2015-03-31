package com.alu.omc.oam.util;

import java.io.IOException;

public interface ICommandExec
{
    public CommandResult execute() throws IOException, InterruptedException; 
   
}
