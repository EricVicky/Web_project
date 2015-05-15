package com.alu.omc.oam.ansible;

public interface AnsibleCall
{
public void prepare(Ansibleworkspace space);

public String asCommand();
}
