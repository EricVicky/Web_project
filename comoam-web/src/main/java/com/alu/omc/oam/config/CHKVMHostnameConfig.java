package com.alu.omc.oam.config;

import java.util.Iterator;
import java.util.Map;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CHKVMHostnameConfig extends KVMCOMConfig{
	
	private Map<String, VMConfig> old_vm_config;
	   
	public Map<String, VMConfig> getOld_vm_config() {
		return old_vm_config;
	}

	public void setOld_vm_config(Map<String, VMConfig> old_vm_config) {
		this.old_vm_config = old_vm_config;
	}

	@Override
	@JsonIgnore 
	public String getVars() {
        Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            VMConfig vmcfg = vm_config.get(name);
	       // VNFHostName.add(vmcfg, this.getComType(), name, this.deployment_prefix);
	        vmcfg.setImgname(this.getVMImageName(name));
	    }
	    Iterator<String> old_it = old_vm_config.keySet().iterator(); 
	    while(old_it.hasNext()){
	        String name = old_it.next();
	        @SuppressWarnings("unchecked")
            VMConfig vmcfg = old_vm_config.get(name);
	        //vmcfg.setImgname(this.getVMImageName(name));
	    }
	   String json = Json2Object.object2Json(this);
       return JsonYamlConverter.convertJson2Yaml(json);
	}
}
