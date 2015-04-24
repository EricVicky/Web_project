package com.alu.omc.oam.config;

import com.alu.omc.oam.ansible.PlaybookCallFact;
import com.alu.omc.oam.util.Json2Object;
import com.fasterxml.jackson.annotation.JsonIgnore;


public abstract class COMConfig implements PlaybookCallFact, COMFact
{
	@Override
    @JsonIgnore 
	public String getCfg() {
		StringBuilder cfg = new StringBuilder("[defaults]");
		cfg.append("\r\n").append("host_key_checking = False");
		cfg.append("\r\n").append("gathering = explicit");
		cfg.append("\r\n").append("timeout=30");
		return cfg.toString();
	}
	
	public String toJson(){
	    return Json2Object.object2Json(this);
	}
	
	
}
