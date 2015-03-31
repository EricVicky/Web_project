package com.alu.omc.oam.ansible;


public interface PlaybookCallFact
{
    public Inventory getInventory();
    public String getVars();
}
