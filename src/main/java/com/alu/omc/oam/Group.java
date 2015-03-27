package com.alu.omc.oam;

import java.util.ArrayList;
import java.util.List;


public class Group implements Entity
{
    String     name;
    List<Entity> hosts = new ArrayList<Entity>();
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void add(Entity entity){
        this.hosts.add(entity);
    }
    
    public List<Entity> getEntities(){
       return hosts;
    }
    public String toString(){
        return "[".concat(name).concat("]");
    }
    
    public Group(String name){
        this.name = name;
    }

    
}