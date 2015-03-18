package com.alu.omc.oam.kvm.model;

import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("serial")
public class KvmFlavor
{
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
@JsonProperty("vcpu")
public int getVcpu()
{
    return vcpu;
}
public void setVcpu(int vCpu)
{
    this.vcpu = vcpu;
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
}
