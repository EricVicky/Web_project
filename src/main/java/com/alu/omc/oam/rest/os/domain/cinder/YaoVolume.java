package com.alu.omc.oam.rest.os.domain.cinder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class YaoVolume implements Serializable {
	private String id;
	private String name;
	private String status;
	private String description;
	private int size;
	private String availabilityZone;
	private String volumeType;
	private Date creationDate;
	private List<YaoVolumeAttachment> attachments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("display_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("display_description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@JsonProperty("availability_zone")
	public String getAvailabilityZone() {
		return availabilityZone;
	}

	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	@JsonProperty("volume_type")
	public String getVolumeType() {
		return volumeType;
	}

	public void setVolumeType(String volumeType) {
		this.volumeType = volumeType;
	}

	@JsonProperty("created_at")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public List<YaoVolumeAttachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<YaoVolumeAttachment> attachments) {
		this.attachments = attachments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attachments == null) ? 0 : attachments.hashCode());
		result = prime
				* result
				+ ((availabilityZone == null) ? 0 : availabilityZone.hashCode());
		result = prime * result
				+ ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + size;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((volumeType == null) ? 0 : volumeType.hashCode());
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
		YaoVolume other = (YaoVolume) obj;
		if (attachments == null) {
			if (other.attachments != null)
				return false;
		} else if (!attachments.equals(other.attachments))
			return false;
		if (availabilityZone == null) {
			if (other.availabilityZone != null)
				return false;
		} else if (!availabilityZone.equals(other.availabilityZone))
			return false;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size != other.size)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (volumeType == null) {
			if (other.volumeType != null)
				return false;
		} else if (!volumeType.equals(other.volumeType))
			return false;
		return true;
	}
}
