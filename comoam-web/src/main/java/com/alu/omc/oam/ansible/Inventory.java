package com.alu.omc.oam.ansible;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alu.omc.oam.kvm.model.Host;

public class Inventory
{
    String      name;
    List<Group> groups = new ArrayList<Group>();
    public static final String ALL_VMS = "allvm:children";
    public static final String NO_OAM = "no_oam:children";
    public static final String OAM = "oam";
    public static final String HOST = "host";
    
    public String toInf()
    {
        StringBuffer inf = new StringBuffer("");
        for(Group group: groups){
            inf.append(group.toString()).append("\n");
            List<Entity> entities = group.getEntities();
            if(entities !=null){
                for(Entity entity : entities){
                    if(entity instanceof Group){
                        inf.append(entity.getName()).append("\n");
                    }else{
                        inf.append(entity.toString()).append("\n");
                    }
                }
            }
        }
        return inf.toString();
    }
    public void addGroup(Group group){
        groups.add(group);
    }
    
    public List<Group> getAllGroups(){
        return groups;
    }
    public static void main(String[] args){
        Inventory inv = new Inventory();
        Group group = new Group("groupA");
        group.add(new Host("user1", "password1", "1.111.111.1"));
        group.add(new Host("user2", "password1", "1.111.111.1"));
        group.add(new Host("user3", "password1", "1.111.111.1"));
        inv.addGroup(group);
        System.out.println(inv.toInf());
    }
    
    public void addNooamGroup(){
        Group nooamgroup = new Group(NO_OAM);
        for(Group group : groups){
           if(!group.getName().equals(ALL_VMS) && !group.getName().equals(OAM) 
        		   && !group.getName().equals(HOST)){
              nooamgroup.add(group); 
           } 
        }
        this.addGroup(nooamgroup);
    }
    
}
