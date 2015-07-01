package com.alu.omc.oam.kvm.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KvmFlavor implements  Serializable
{
private static final long serialVersionUID = 3742611248807337883L;
private int memory;
private int vcpu;
private int disk;
@JsonProperty("memory")
public int getMemory()
{
    return memory;
}
public void setMemory(int memory)
{
    this.memory = memory;
}
@JsonProperty("vCpu")
public int getVcpu()
{
    return vcpu;
}
public void setVcpu(int vCpu)
{
    this.vcpu = vCpu;
}
@JsonProperty("disk")
public int getDisk()
{
    return disk;
}
public void setDisk(int disk)
{
    this.disk = disk;
}
public KvmFlavor(int memory, int vcpu, int disk)
{
    super();
    this.memory = memory;
    this.vcpu = vcpu;
    this.disk = disk;
}

public KvmFlavor(){
    
}
}
