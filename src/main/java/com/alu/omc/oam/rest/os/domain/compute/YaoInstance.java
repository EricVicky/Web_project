package com.alu.omc.oam.rest.os.domain.compute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openstack4j.model.compute.Address;
import org.openstack4j.model.compute.Server;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class YaoInstance implements Serializable {
    private String name;
    private String id;
    private List<String> addresses;  
    private String status;
    
    private Date updated;
    private Date created;
    
    private String flavor;
    private String image;
    
    private String keypair;
    private String availabilityZone;
    
    private String stackName;

	public YaoInstance(){}
    
    public YaoInstance(Server server)
    {
    	this.setId(server.getId());
    	this.setName(server.getName());
    	this.setCreated(server.getCreated());
    	this.setUpdated(server.getUpdated());
    	this.setStatus(server.getStatus().toString());
        //this.setFlavor(server.getFlavor().getName());
        //this.setImage(server.getImage().getName());
    	this.setKeypair(server.getKeyName());
    	this.setAvailabilityZone(server.getAvailabilityZone());

        List<String> addressList = new ArrayList<String>();
        Map<String, List<? extends Address>> addresses = server.getAddresses()
                .getAddresses();
        for (String addressName : addresses.keySet()) {
            List<? extends Address> addressListInMap = (List<? extends Address>) addresses
                    .get(addressName);
            for (Address address : addressListInMap) {
                addressList.add(address.getAddr());
            }
        }
        this.setAddresses(addressList);
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getUpdated() {
        return updated;
    }
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public String getFlavor() {
        return flavor;
    }
    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public List<String> getAddresses() {
        return addresses;
    }
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }
    @JsonProperty("key_name")
	public String getKeypair() {
		return keypair;
	}
	public void setKeypair(String keypair) {
		this.keypair = keypair;
	}
	@JsonProperty("availability_zone")
	public String getAvailabilityZone() {
		return availabilityZone;
	}
	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}
	
    @JsonProperty("stack_name")
    public String getStackName() {
    	if(name.contains("-")) {
    		return name.substring(0, name.indexOf("-"));
    	} else {
    		return "";
    	}
    }
    
    public String getVNFName() {
    	return getStackName();
    }
    
    public String getVNFC() {
    	return name.substring(name.indexOf("-")+1, name.length());
    }

	public void setStackName(String stackName) {
		this.stackName = stackName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((addresses == null) ? 0 : addresses.hashCode());
		result = prime
				* result
				+ ((availabilityZone == null) ? 0 : availabilityZone.hashCode());
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((flavor == null) ? 0 : flavor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((keypair == null) ? 0 : keypair.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((stackName == null) ? 0 : stackName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updated == null) ? 0 : updated.hashCode());
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
		YaoInstance other = (YaoInstance) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (availabilityZone == null) {
			if (other.availabilityZone != null)
				return false;
		} else if (!availabilityZone.equals(other.availabilityZone))
			return false;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (flavor == null) {
			if (other.flavor != null)
				return false;
		} else if (!flavor.equals(other.flavor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (keypair == null) {
			if (other.keypair != null)
				return false;
		} else if (!keypair.equals(other.keypair))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stackName == null) {
			if (other.stackName != null)
				return false;
		} else if (!stackName.equals(other.stackName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updated == null) {
			if (other.updated != null)
				return false;
		} else if (!updated.equals(other.updated))
			return false;
		return true;
	}
	
	
    
}
