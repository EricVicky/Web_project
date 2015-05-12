package com.alu.omc.oam.ansible;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.kvm.model.HostStatus;

@Component
public class RunningHostLock
{
private Map<Host, HostStatus> lockedHosts = new HashMap<Host, HostStatus>();

public void lock(Host host, Action action  ){
    lockedHosts.put(host, new HostStatus(action, new Date()));
}

public void unlock(Host host){
    lockedHosts.remove(host);
}

public boolean isLocked(Host host){
    return lockedHosts.containsKey(host);
}

public HostStatus getAction(Host host){
   return lockedHosts.get(host); 
}

}
