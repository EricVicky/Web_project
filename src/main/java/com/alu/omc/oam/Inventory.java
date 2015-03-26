package com.alu.omc.oam;

import java.util.List;

public class Inventory
{
String name;
List<Group> groups;

public String toInf(){
    return null;
}
public class Group{
    String name;
    List<Host> hosts;
}

public class Host{
    String name;
    String ip_address;
}
}
