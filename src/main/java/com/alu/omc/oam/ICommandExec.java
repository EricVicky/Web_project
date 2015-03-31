package com.alu.omc.oam;

import java.io.IOException;

import com.alu.omc.oam.util.CommandResult;

public interface ICommandExec
{
    public CommandResult execute() throws IOException, InterruptedException; 
   
}
