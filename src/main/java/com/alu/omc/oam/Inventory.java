package com.alu.omc.oam;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class Inventory
{
    String      name;
    List<Group> groups = new ArrayList<Group>();

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
    
    public interface Entity{
        public String getName();
        public void setName(String name);
    }

    public class Host implements Entity
    {
        String name;
        String ip_address;
        String user = "";
        String password = "";

        public String getName()
        {
            return name;
        }
        
        public Host(String user, String password, String ip_address){
            this.user = user;
            this.password = password;
            this.ip_address = ip_address;
                    
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getIp_address()
        {
            return ip_address;
        }

        public void setIp_address(String ip_address)
        {
            this.ip_address = ip_address;
        }

        public String getUser()
        {
            return user;
        }

        public void setUser(String user)
        {
            this.user = user;
        }

        public String getPassword()
        {
            return password;
        }

        public void setPassword(String password)
        {
            this.password = password;
        }
        
        public String toString()
        {
            return StringUtils.join(new String[] { this.ip_address,
                    "ansible_ssh_user=".concat(this.user),
                    "ansible_ssh_password=".concat(this.password) }, " ");
        }

    }

    public static void main(String[] args){
        Inventory inv = new Inventory();
        Group group = inv.new Group("groupA");
        group.add(inv.new Host("user1", "password1", "1.111.111.1"));
        group.add(inv.new Host("user2", "password1", "1.111.111.1"));
        group.add(inv.new Host("user3", "password1", "1.111.111.1"));
        inv.addGroup(group);
        System.out.println(inv.toInf());
    }
    
}
