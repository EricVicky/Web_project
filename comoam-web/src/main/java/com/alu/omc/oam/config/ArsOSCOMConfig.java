package com.alu.omc.oam.config;

import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArsOSCOMConfig extends OVMCOMConfig {

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
	
	@Override
	@JsonIgnore 
	public Inventory getInventory() 
	{
		Inventory inv = new Inventory();
	    Group hostg = new Group("host");
	    hostg.add(new Host("localhost"));
	    inv.addGroup(hostg);
	    @SuppressWarnings("unchecked")
        Iterator<String> it = vm_config.keySet().iterator(); 
	    Group allVM = new Group(Inventory.ALL_VMS);
	    inv.addGroup(allVM);
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        String ipAddress = vmcfg.get("provider_ip_address");
	        Group g = new Group(name);
	        allVM.add(g);
	        g.add(new Host(ipAddress));
	        inv.addGroup(g);
	    }
		return inv;
	}
	
	
	public String getLicense_key() {
		return license_key;
	}


	public void setLicense_key(String license_key) {
		this.license_key = license_key;
	}


	private String license_key;
	private Map vm_config;
	public Map getVm_config() {
		return vm_config;
	}


	public void setVm_config(Map vm_config) {
		this.vm_config = vm_config;
	}
	
	

}
