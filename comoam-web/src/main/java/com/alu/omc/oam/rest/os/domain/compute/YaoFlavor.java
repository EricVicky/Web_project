package com.alu.omc.oam.rest.os.domain.compute;

import java.io.Serializable;

import org.openstack4j.model.compute.Flavor;

@SuppressWarnings("serial")
public class YaoFlavor implements Serializable {
    private String name;
   	private int vcpus;
    private int disk;
    private int ephemeraldisk;
    private int totaldisk;
    private int ram;
        
	    
    public YaoFlavor(Flavor flavor)
    {
    	name = flavor.getName();
        vcpus   = flavor.getVcpus();
        disk = flavor.getDisk();
        ephemeraldisk = flavor.getEphemeral();
        totaldisk = flavor.getDisk()+flavor.getEphemeral();
        ram = flavor.getRam();
    }
    
    public void setFlavor(YaoFlavor flavor) {
    	name = flavor.getName();
        vcpus   = flavor.getVcpus();
        disk = flavor.getDisk();
        ephemeraldisk = flavor.getEphemeraldisk();
        totaldisk = flavor.getTotaldisk();
        ram = flavor.getRam();
    }

  

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVcpus() {
		return vcpus;
	}

	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

	public int getEphemeraldisk() {
		return ephemeraldisk;
	}

	public void setEphemeraldisk(int ephemeraldisk) {
		this.ephemeraldisk = ephemeraldisk;
	}

	public int getTotaldisk() {
		return totaldisk;
	}

	public void setTotaldisk(int totaldisk) {
		this.totaldisk = totaldisk;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}
   
}
