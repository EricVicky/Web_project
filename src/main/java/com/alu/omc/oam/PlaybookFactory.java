package com.alu.omc.oam;

import java.util.HashMap;
import java.util.Map;

public class PlaybookFactory
{
    private  static PlaybookFactory instance = null;
    private static final Map<String, Playbook> playbooks = new HashMap<String, Playbook>(20);
    static{
        playbooks.put(key(Environment.KVM, Action.INSTALL), new Playbook("install_kvm.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.INSTALL), new Playbook("install_os.yml") );
    }
    public Playbook getPlaybook(Action action, COMFact fact){
       return null;
    }
    
    public static PlaybookFactory getInstance(){
        if(instance == null){
            instance = new PlaybookFactory();
        }
        return instance;
    }
    
    private static String key(Environment env, Action action){
        return env.toString().concat(action.toString());
    }
    
    private PlaybookFactory(){}
}
