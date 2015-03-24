package com.alu.omc.oam.os.config;

import com.alu.omc.oam.COMType;
import com.alu.omc.oam.Environment;

public interface InstallConfig 
{
    public Environment getEnvironment();
    public COMType getCOMType();
}
