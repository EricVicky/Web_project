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
	
	
	public String getLicense() {
		return license;
	}


	public void setLicense(String license) {
		this.license = license;
	}


	private String license;

}
