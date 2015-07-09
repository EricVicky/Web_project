package com.alu.omc.oam.util;

import java.io.Serializable;

public class Timezone implements Serializable {

    private static final long serialVersionUID = -5328129422350794431L;
    String label;
    String id;
    public Timezone(){
        
    }
    public String getLabel()
    {
        return label;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
}