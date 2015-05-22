package com.alu.omc.oam.rest.os.domain.compute;

import org.openstack4j.model.compute.AbsoluteLimit;
import org.openstack4j.model.storage.block.BlockLimits.Absolute;

public class YaoLimits {
    private int maxTotalCores;
    private int maxTotalInstances;
    private int maxTotalRAMSize;
    private int maxTotalVolumeGigabytes;
    private int totalInstancesUsed;
    private int totalCoresUsed;
    private int totalRAMUsed;
    private int totalVolumesUsed;
    
    public YaoLimits(){}
    
    public YaoLimits(AbsoluteLimit limits) {      
        maxTotalCores = limits.getMaxTotalCores();
        maxTotalInstances = limits.getMaxTotalInstances();
        maxTotalRAMSize = limits.getMaxTotalRAMSize();
        maxTotalVolumeGigabytes = limits.getMaxTotalVolumeGigabytes();
        totalInstancesUsed = limits.getTotalInstancesUsed();
        totalCoresUsed = limits.getTotalCoresUsed();
        totalRAMUsed = limits.getTotalRAMUsed();
        totalVolumesUsed = limits.getTotalVolumesUsed();
    }

    public YaoLimits addStorageLimits(Absolute absolute) {
        maxTotalVolumeGigabytes = absolute.getMaxTotalVolumeGigabytes();
        totalVolumesUsed = absolute.getTotalGigabytesUsed();
        return this;
    }

    //XXX: auto generated methods
    public int getMaxTotalCores() {
        return maxTotalCores;
    }

    public void setMaxTotalCores(int maxTotalCores) {
        this.maxTotalCores = maxTotalCores;
    }

    public int getMaxTotalInstances() {
        return maxTotalInstances;
    }

    public void setMaxTotalInstances(int maxTotalInstances) {
        this.maxTotalInstances = maxTotalInstances;
    }

    public int getMaxTotalRAMSize() {
        return maxTotalRAMSize;
    }

    public void setMaxTotalRAMSize(int maxTotalRAMSize) {
        this.maxTotalRAMSize = maxTotalRAMSize;
    }

    public int getMaxTotalVolumeGigabytes() {
        return maxTotalVolumeGigabytes;
    }

    public void setMaxTotalVolumeGigabytes(int maxTotalVolumeGigabytes) {
        this.maxTotalVolumeGigabytes = maxTotalVolumeGigabytes;
    }

    public int getTotalInstancesUsed() {
        return totalInstancesUsed;
    }

    public void setTotalInstancesUsed(int totalInstancesUsed) {
        this.totalInstancesUsed = totalInstancesUsed;
    }

    public int getTotalCoresUsed() {
        return totalCoresUsed;
    }

    public void setTotalCoresUsed(int totalCoresUsed) {
        this.totalCoresUsed = totalCoresUsed;
    }

    public int getTotalRAMUsed() {
        return totalRAMUsed;
    }

    public void setTotalRAMUsed(int totalRAMUsed) {
        this.totalRAMUsed = totalRAMUsed;
    }

    public int getTotalVolumesUsed() {
        return totalVolumesUsed;
    }

    public void setTotalVolumesUsed(int totalVolumesUsed) {
        this.totalVolumesUsed = totalVolumesUsed;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxTotalCores;
		result = prime * result + maxTotalInstances;
		result = prime * result + maxTotalRAMSize;
		result = prime * result + maxTotalVolumeGigabytes;
		result = prime * result + totalCoresUsed;
		result = prime * result + totalInstancesUsed;
		result = prime * result + totalRAMUsed;
		result = prime * result + totalVolumesUsed;
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
		YaoLimits other = (YaoLimits) obj;
		if (maxTotalCores != other.maxTotalCores)
			return false;
		if (maxTotalInstances != other.maxTotalInstances)
			return false;
		if (maxTotalRAMSize != other.maxTotalRAMSize)
			return false;
		if (maxTotalVolumeGigabytes != other.maxTotalVolumeGigabytes)
			return false;
		if (totalCoresUsed != other.totalCoresUsed)
			return false;
		if (totalInstancesUsed != other.totalInstancesUsed)
			return false;
		if (totalRAMUsed != other.totalRAMUsed)
			return false;
		if (totalVolumesUsed != other.totalVolumesUsed)
			return false;
		return true;
	}   
    
}
