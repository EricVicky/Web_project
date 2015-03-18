package com.alu.omc.oam.rest.os.domain.instance.detail;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.ServerCreate.SecurityGroup;
import org.openstack4j.model.storage.block.Volume;

import com.alu.omc.oam.rest.os.domain.compute.YaoFlavor;
import com.alu.omc.oam.rest.os.domain.compute.YaoInstance;

@SuppressWarnings("serial")
public class YaoInstanceDetail implements Serializable {
	private YaoInstance instance;
	private YaoFlavor flavor;
	private List<SecurityGroup> securityGroups;
	private List<Volume> volumes;
	private Map<String, String> metaData;
	private Map<String, List<? extends Address>> addresses;
	public YaoInstance getInstance() {
		return instance;
	}
	public void setInstance(YaoInstance instance) {
		this.instance = instance;
	}
	public YaoFlavor getFlavor() {
		return flavor;
	}
	public void setFlavor(YaoFlavor flavor) {
		this.flavor = flavor;
	}
	public List<SecurityGroup> getSecurityGroups() {
		return securityGroups;
	}
	public void setSecurityGroups(List<SecurityGroup> securityGroups) {
		this.securityGroups = securityGroups;
	}
	public List<Volume> getVolumes() {
		return volumes;
	}
	public void setVolumes(List<Volume> volumes) {
		this.volumes = volumes;
	}
	public Map<String, String> getMetaData() {
		return metaData;
	}
	public void setMetaData(Map<String, String> metaData) {
		this.metaData = metaData;
	}
	public Map<String, List<? extends Address>> getAddresses() {
		return addresses;
	}
	public void setAddresses(Map<String, List<? extends Address>> addresses) {
		this.addresses = addresses;
	}
}
