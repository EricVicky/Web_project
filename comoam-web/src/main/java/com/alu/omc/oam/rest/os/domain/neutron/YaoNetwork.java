package com.alu.omc.oam.rest.os.domain.neutron;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YaoNetwork 
{
	private String id;
	private String name;
	private String status;
	private boolean shared;
	@JsonProperty("admin_state_up")
	private boolean adminStatusUp;
	@JsonProperty("neutron_subnets")
	private List<YaoSubnet> neutronSubnets;
	
	
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return the shared
     */
    public boolean isShared() {
        return shared;
    }
    /**
     * @param shared the shared to set
     */
    public void setShared(boolean shared) {
        this.shared = shared;
    }
    /**
     * @return the adminStatusUp
     */
    public boolean isAdminStatusUp() {
        return adminStatusUp;
    }
    /**
     * @param adminStatusUp the adminStatusUp to set
     */
    public void setAdminStatusUp(boolean adminStatusUp) {
        this.adminStatusUp = adminStatusUp;
    }
    /**
     * @return the neutronSubnets
     */
    public List<YaoSubnet> getNeutronSubnets() {
        return neutronSubnets;
    }
    /**
     * @param neutronSubnets the neutronSubnets to set
     */
    public void setNeutronSubnets(List<YaoSubnet> neutronSubnets) {
        this.neutronSubnets = neutronSubnets;
    }
	
	
}
