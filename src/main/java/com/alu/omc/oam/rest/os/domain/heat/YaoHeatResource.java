package com.alu.omc.oam.rest.os.domain.heat;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YaoHeatResource {
	private Date updatedTime;
	private String resourceType;
	private String resourceStatusReason;
	private String resourceName;
	private String logicalResourceId;
	private String resourceStatus;
	private String physicalResourceId;
	private List<String> requiredBy;
	
	@JsonProperty("updated_time")
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	@JsonProperty("resource_type")
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	@JsonProperty("resource_status_reason")
	public String getResourceStatusReason() {
		return resourceStatusReason;
	}
	public void setResourceStatusReason(String resourceStatusReason) {
		this.resourceStatusReason = resourceStatusReason;
	}
	@JsonProperty("resource_name")
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	@JsonProperty("logical_resource_id")
	public String getLogicalResourceId() {
		return logicalResourceId;
	}
	public void setLogicalResourceId(String logicalResourceId) {
		this.logicalResourceId = logicalResourceId;
	}
	@JsonProperty("resource_status")
	public String getResourceStatus() {
		return resourceStatus;
	}
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}
	@JsonProperty("physical_resource_id")
	public String getPhysicalResourceId() {
		return physicalResourceId;
	}
	public void setPhysicalResourceId(String physicalResourceId) {
		this.physicalResourceId = physicalResourceId;
	}
	@JsonProperty("required_by")
	public List<String> getRequiredBy() {
		return requiredBy;
	}
	public void setRequiredBy(List<String> requiredBy) {
		this.requiredBy = requiredBy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((logicalResourceId == null) ? 0 : logicalResourceId
						.hashCode());
		result = prime
				* result
				+ ((physicalResourceId == null) ? 0 : physicalResourceId
						.hashCode());
		result = prime * result
				+ ((requiredBy == null) ? 0 : requiredBy.hashCode());
		result = prime * result
				+ ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result
				+ ((resourceStatus == null) ? 0 : resourceStatus.hashCode());
		result = prime
				* result
				+ ((resourceStatusReason == null) ? 0 : resourceStatusReason
						.hashCode());
		result = prime * result
				+ ((resourceType == null) ? 0 : resourceType.hashCode());
		result = prime * result
				+ ((updatedTime == null) ? 0 : updatedTime.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YaoHeatResource other = (YaoHeatResource) obj;
		if (logicalResourceId == null) {
			if (other.logicalResourceId != null)
				return false;
		} else if (!logicalResourceId.equals(other.logicalResourceId))
			return false;
		if (physicalResourceId == null) {
			if (other.physicalResourceId != null)
				return false;
		} else if (!physicalResourceId.equals(other.physicalResourceId))
			return false;
		if (requiredBy == null) {
			if (other.requiredBy != null)
				return false;
		} else if (!requiredBy.equals(other.requiredBy))
			return false;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		if (resourceStatus == null) {
			if (other.resourceStatus != null)
				return false;
		} else if (!resourceStatus.equals(other.resourceStatus))
			return false;
		if (resourceStatusReason == null) {
			if (other.resourceStatusReason != null)
				return false;
		} else if (!resourceStatusReason.equals(other.resourceStatusReason))
			return false;
		if (resourceType == null) {
			if (other.resourceType != null)
				return false;
		} else if (!resourceType.equals(other.resourceType))
			return false;
		if (updatedTime == null) {
			if (other.updatedTime != null)
				return false;
		} else if (!updatedTime.equals(other.updatedTime))
			return false;
		return true;
	}
	
	
}
