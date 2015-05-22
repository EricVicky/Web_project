package com.alu.omc.oam.rest.os.domain.neutron;

import java.util.List;

import org.openstack4j.model.network.IP;
import org.openstack4j.model.network.State;

import com.fasterxml.jackson.annotation.JsonProperty;


public class YaoPort
{
	
	
	private String name;
	@JsonProperty("fixed_ips")
	private List<IP> fixedIps;
	@JsonProperty("device_owner")
	private String devOwner;	
	private String networkId;
	@JsonProperty("status")
	private State state;
	@JsonProperty("device_id")
	private String deviceId;
	@JsonProperty("mac_address")
	private String macAdress;
	@JsonProperty("security_groups")
	private List<String> securityGroup;
	@JsonProperty("id")
	private String portId;
	@JsonProperty("tennant_id")
	private String tennantId;
	@JsonProperty("admin_state_up")
	private boolean adminStatusUp;
	
	

	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 */
	public List<IP> getFixedIps() {
		return fixedIps;
	}
	/**
	 * @param fixedIp
	 */
	public void setFixedIps(List<IP> fixedIps) {
		this.fixedIps = fixedIps;
	}
	
	/**
	 * @return
	 */
	public String getDevOwner() {
		return devOwner;
	}

	/**
	 * @param devOwner
	 */
	public void setDevOwner(String devOwner) {
		this.devOwner = devOwner;
	}

	/**
	 * @return
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * @param networkId
	 */
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	
	/**
	 * @return
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * @return
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return
	 */
	public String getMacAdress() {
		return macAdress;
	}

	/**
	 * @param macAdress
	 */
	public void setMacAdress(String macAdress) {
		this.macAdress = macAdress;
	}

	/**
	 * @return
	 */
	public List<String> getSecurityGroup() {
		return securityGroup;
	}

	/**
	 * @param securityGroup
	 */
	public void setSecurityGroup(List<String> securityGroup) {
		this.securityGroup = securityGroup;
	}

	/**
	 * @return
	 */
	public String getPortId() {
		return portId;
	}

	/**
	 * @param portId
	 */
	public void setPortId(String portId) {
		this.portId = portId;
	}

	/**
	 * @return
	 */
	public String getTennantId() {
		return tennantId;
	}

	/**
	 * @param tennantId
	 */
	public void setTennantId(String tennantId) {
		this.tennantId = tennantId;
	}
	
	/**
	 * @return
	 */
	public boolean isAdminStatusUp() {
		return adminStatusUp;
	}
	/**
	 * @param adminStatusUp
	 */
	public void setAdminStatusUp(boolean adminStatusUp) {
		this.adminStatusUp = adminStatusUp;
	}
	

}

