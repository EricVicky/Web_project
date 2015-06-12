package com.alu.omc.oam.config;

import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OVMCOMConfig extends COMConfig {

	@Override
	@JsonIgnore 
	public Inventory getInventory() {
		Inventory inv = new Inventory();
	    Group hostg = new Group("host");
	    hostg.add(new Host(this.active_host_ip));
	    inv.addGroup(hostg);
	    @SuppressWarnings("unchecked")
        Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        String ipAddress = vmcfg.get("ip_address");
	        Group g = new Group(name);
	        g.add(new Host(ipAddress));
	        inv.addGroup(g);
	    }
		return inv;
	}

	@Override
	@JsonIgnore 
	public String getVars() {
		Yaml yaml = new Yaml();
        return YamlFormatterUtil.format(yaml.dump(this));	
	}

	@Override
	public Environment getEnvironment() {
		return Environment.KVM_OVM;
	}

	@Override
	public COMType getCOMType() {
		return COMType.OVM;
	}

	@Override
	public String getStackName() {
		return this.deployment_prefix; 
	}
	
	private String active_host_ip = "";
	private String vm_img_dir = "";
	private String deployment_prefix = "";
	private String atc_switches = "";
	private Map<String, Object> vm_config;

	public Host getHost(){
		return new Host(active_host_ip);
	}
	
	public String getActive_host_ip() {
		return active_host_ip;
	}

	public void setActive_host_ip(String active_host_ip) {
		this.active_host_ip = active_host_ip;
	}
	
	public String getVm_img_dir() {
		return vm_img_dir;
	}

	public void setVm_img_dir(String vm_img_dir) {
		this.vm_img_dir = vm_img_dir;
	}

	public String getDeployment_prefix() {
		return deployment_prefix;
	}

	public void setDeployment_prefix(String deployment_prefix) {
		this.deployment_prefix = deployment_prefix;
	}

	public String getAtc_switches() {
		return atc_switches;
	}

	public void setAtc_switches(String atc_switches) {
		this.atc_switches = atc_switches;
	}

	public Map<String, Object> getVm_config() {
		return vm_config;
	}

	public void setVm_config(Map<String, Object> vm_config) {
		this.vm_config = vm_config;
	}
	
	

}
