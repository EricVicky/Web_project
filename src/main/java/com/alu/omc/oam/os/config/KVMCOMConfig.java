package com.alu.omc.oam.os.config;

import java.io.Serializable;
import java.util.Map;

import com.alu.omc.oam.AnsibleVars;
import com.alu.omc.oam.COMConfig;
import com.alu.omc.oam.COMType;
import com.alu.omc.oam.Environment;
import com.alu.omc.oam.Inventory;

public class KVMCOMConfig extends COMConfig implements Serializable{
	  
	
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
    private COMType            comType;
	private Map vm_config;
	private boolean support_gr;
	private String timezone;
	private String active_host_ip;
	private String deployment_prefix;
	
	
	@Override
    public Environment getEnvironment()
    {
        // TODO Auto-generated method stub
        return Environment.KVM;
    }

    @Override
    public COMType getCOMType()
    {
        // TODO Auto-generated method stub
        return comType;
    }
	public String getDeployment_prefix() {
		return deployment_prefix;
	}

	public void setDeployment_prefix(String deployment_prefix) {
		this.deployment_prefix = deployment_prefix;
	}

	public String getActive_host_ip() {
		return active_host_ip;
	}

	public void setActive_host_ip(String active_host_ip) {
		this.active_host_ip = active_host_ip;
	}
	
    public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public boolean getSupport_gr() {
		return support_gr;
	}

	public void setSupport_gr(boolean support_gr) {
		this.support_gr = support_gr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public COMType getComType()
    {
        return comType;
    }

    public void setComType(COMType comType)
    {
        this.comType = comType;
    }
    
    public Map getVm_config()
    {
        return vm_config;
    }

    public void setVm_config(Map vm_config)
    {
        this.vm_config = vm_config;
    }

	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnsibleVars getVars() {
		// TODO Auto-generated method stub
		return null;
	}
    

    
    
    
}
