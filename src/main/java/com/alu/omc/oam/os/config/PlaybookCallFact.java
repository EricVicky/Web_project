package com.alu.omc.oam.os.config;

import com.alu.omc.oam.AnsibleVars;
import com.alu.omc.oam.Inventory;

public interface PlaybookCallFact 
{
    public Inventory getInventory();
    public AnsibleVars getVars();
}
