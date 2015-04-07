package com.alu.omc.oam.config;

import com.alu.omc.oam.ansible.PlaybookCallFact;
import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class COMConfig implements PlaybookCallFact, COMFact
{
	@Override
    @JsonIgnore 
	public String getCfg() {
		StringBuilder cfg = new StringBuilder("[defaults]");
		cfg.append("\r\n").append("host_key_checking = False");
		return cfg.toString();
	}
	
	
}
