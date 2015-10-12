package com.alu.omc.oam.config;


import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.util.Json2Object;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class FullBackupConfig<T> extends COMConfig
{
public String stackName;
//public BackupLocation location;

@JsonIgnore
public COMStackService comstackService;
    
    @Override
    @JsonIgnore
    public Inventory getInventory()
    {
        return getComconfig().getInventory();
    }

    @Override
    @JsonIgnore
    public String getVars()
    {
    	//String location = Json2Object.object2Json(location);
    	//return getComconfig().getVars() + location;
    	return getComconfig().getVars();
    }

    @Override
    @JsonIgnore
    public Environment getEnvironment()
    {
       return getComconfig().getEnvironment();
    }
    @JsonIgnore
    private COMConfig getComconfig(){
        COMStack comStack = comstackService.get(stackName); 
         @SuppressWarnings("unchecked")
        COMConfig config = (COMConfig)new Json2Object<T>().toMap(comStack.getComConfig());
         return config;
    }

    @Override
    public COMType getCOMType()
    {
        return  getComconfig().getCOMType() ;
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
