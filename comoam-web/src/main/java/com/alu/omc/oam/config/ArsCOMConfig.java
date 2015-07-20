package com.alu.omc.oam.config;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArsCOMConfig extends OVMCOMConfig {

	@Override
	public COMType getCOMType() {
		return COMType.ARS;
	}
	

	@Override
	@JsonIgnore 
	public String getVars() {
		Yaml yaml = new Yaml();
        return YamlFormatterUtil.format(yaml.dump(this));	
	}
	
	
	public String getLicense_key() {
		return license_key;
	}


	public void setLicense_key(String license_key) {
		this.license_key = license_key;
	}


	private String license_key;

}
