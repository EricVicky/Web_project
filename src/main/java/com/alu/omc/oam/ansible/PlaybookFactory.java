package com.alu.omc.oam.ansible;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMFact;
import com.alu.omc.oam.config.Environment;

public class PlaybookFactory
{
    private  static PlaybookFactory instance = null;
    private static final Map<String, Playbook> playbooks = new HashMap<String, Playbook>(20);
    private static Logger log = LoggerFactory.getLogger(PlaybookFactory.class);

    static{
        playbooks.put(key(Environment.KVM, Action.INSTALL), new Playbook("install_kvm.yml") );
        playbooks.put(key(Environment.KVM, Action.UPGRADE), new Playbook("upgrade.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.INSTALL), new Playbook("install_os.yml") );
    }

    public Playbook getPlaybook(Action action, COMFact fact) {
    	if (fact.getEnvironment() == Environment.KVM) {
    		if (action == Action.INSTALL) {
    			return playbooks.get(key(Environment.KVM, Action.INSTALL));
    		}
    		else if (action == Action.UPGRADE){
    			return playbooks.get(key(Environment.KVM, Action.UPGRADE));
    		}
    	}
    	else if (fact.getEnvironment() == Environment.OPENSTACK) {
    		if (action == Action.INSTALL) {
    			return playbooks.get(key(Environment.OPENSTACK, Action.INSTALL));
    		}
    	}
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
