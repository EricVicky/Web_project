package com.alu.omc.oam;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConfigProperty implements Serializable
{
    private String name;
    private String value;
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getValue()
    {
        return value;
    }
    public void setValue(String value)
    {
        this.value = value;
    }
}
