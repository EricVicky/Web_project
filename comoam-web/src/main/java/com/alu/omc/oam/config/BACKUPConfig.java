package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BACKUPConfig<T extends COMConfig> extends COMConfig implements Serializable{
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
	private T config;
	private String filename;
	private String dir;

	public T getConfig() {
		return config;
	}
	public void setConfig(T config) {
		this.config = config;
	}
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	@Override
	public Inventory getInventory() {
		return config.getInventory();
	}

	@Override
	public String getVars() {
		return config.getVars()+"filename:"+getFilename()+
				'\n'+"directory:"+getDir();
	}

	@Override
	public Environment getEnvironment() {
		return config.getEnvironment();
	}

	@Override
	public COMType getCOMType() {
		return config.getCOMType();
	}

	@Override
	public String getStackName() {
		return config.getStackName();
	}

}
