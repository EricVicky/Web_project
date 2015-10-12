package com.alu.omc.oam.config;


import javax.annotation.Resource;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FullBackupConfig<T extends COMConfig> extends COMConfig
{
	public String stackName;
	private FullBackupLocation backupLocation;
	private T config;
	@Resource
	COMStackService cOMStackService;
    
    public FullBackupLocation getBackupLocation() {
		return backupLocation;
	}

	public void setBackupLocation(FullBackupLocation backupLocation) {
		this.backupLocation = backupLocation;
	}
	 @JsonIgnore
	public T getConfig() {
		return config;
	}

	public void setConfig(T config) {
		this.config = config;
	}

	@Override
    @JsonIgnore
    public Inventory getInventory()
    {
        return config.getInventory();
    }

    @Override
    @JsonIgnore
    public String getVars()
    {	
    	Yaml yaml = new Yaml();
        return config.getVars()+YamlFormatterUtil.formatbackup(yaml.dump(this.backupLocation));
    }

    @Override
    @JsonIgnore
    public Environment getEnvironment()
    {
       return config.getEnvironment();
    }


    @Override
    public COMType getCOMType()
    {
        return  config.getCOMType() ;
    }

    @Override
    public String getStackName()
    {
        return this.stackName;
    }

    public void setStackName(String stackName)
    {
        this.stackName = stackName;
    }

}
