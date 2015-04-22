package com.alu.omc.oam.ansible;

import java.util.HashSet;

import org.springframework.stereotype.Component;

@Component
public class AnsibleRuningContext
{
private HashSet<Host> lockedHosts = new HashSet<Host>();

public void lock(Host host){
    lockedHosts.add(host);
}

public void unlock(Host host){
    lockedHosts.remove(host);
}

public boolean isLocked(Host host){
    return lockedHosts.contains(host);
}
}
