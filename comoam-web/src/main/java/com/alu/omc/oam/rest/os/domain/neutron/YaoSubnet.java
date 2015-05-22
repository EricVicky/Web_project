package com.alu.omc.oam.rest.os.domain.neutron;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YaoSubnet 
{
	private String id;
	private String name;
	private String networkId;
	private String cidr;
	private boolean enableDhcp;
	private String ipVersion;
	private String gatewayIp;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty("network_id")
	public String getNetworkId() {
		return networkId;
	}
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}
	public String getCidr() {
		return cidr;
	}
	public void setCidr(String cidr) {
		this.cidr = cidr;
	}
	@JsonProperty("enable_dhcp")
	public boolean isEnableDhcp() {
		return enableDhcp;
	}
	public void setEnableDhcp(boolean enableDhcp) {
		this.enableDhcp = enableDhcp;
	}
	@JsonProperty("ip_version")
	public String getIpVersion() {
		return ipVersion;
	}
	public void setIpVersion(String ipVersion) {
		this.ipVersion = ipVersion;
	}
	@JsonProperty("gateway_ip")
	public String getGatewayIp() {
		return gatewayIp;
	}
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
	
}
