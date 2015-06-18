package com.alu.omc.oam.ansible;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionKey;
import com.alu.omc.oam.config.COMFact;
import com.alu.omc.oam.config.COMType;
import com.alu.omc.oam.config.Environment;

public class PlaybookFactory
{
    private  static PlaybookFactory instance = null;
    private static final Map<String, Playbook> playbooks = new HashMap<String, Playbook>(20);
    private static Logger log = LoggerFactory.getLogger(PlaybookFactory.class);

    static{
        playbooks.put(key(Environment.KVM, Action.INSTALL), new Playbook("deploy.yml") );
        playbooks.put(key(Environment.KVM, Action.UPGRADE), new Playbook("upgrade.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.INSTALL), new Playbook("deploy.yml") );
        playbooks.put(key(Environment.KVM, Action.BACKUP), new Playbook("backup.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.BACKUP), new Playbook("backup.yml") );
        playbooks.put(key(Environment.KVM, Action.DELETE), new Playbook("destroy.yml") );
        playbooks.put(key(Environment.KVM, Action.GRINST_PRI), new Playbook("gr_pri_install.yml") );
        playbooks.put(key(Environment.KVM, Action.GRINST_SEC), new Playbook("gr_sec_install.yml") );
        playbooks.put(key(Environment.KVM, Action.GRUNINST), new Playbook("gr_uninstall.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.UPGRADE), new Playbook("upgrade.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.DELETE), new Playbook("destroy.yml") );
        playbooks.put(key(Environment.KVM, Action.RESTORE), new Playbook("restore.yml") );
        playbooks.put(key(Environment.OPENSTACK, Action.RESTORE), new Playbook("restore.yml") );
        playbooks.put(key(Environment.KVM, Action.INSTALL), new Playbook("ovm_install_kvm.yml") );
        playbooks.put(key(Environment.KVM, Action.INSTALL, COMType.HPSIM), new Playbook("ovm_install_kvm.yml") );
        playbooks.put(key(Environment.KVM, Action.INSTALL, COMType.ATC), new Playbook("ovm_install_kvm.yml") );
        playbooks.put(key(Environment.KVM, Action.INSTALL, COMType.QOSAC), new Playbook("deploy_qosac.yml") );
    }

    public Playbook getPlaybook(Action action, COMFact fact) {
        return playbooks.get(key(fact.getEnvironment(), action, fact.getCOMType()));
    }
    
    public static PlaybookFactory getInstance(){
        if(instance == null){
            instance = new PlaybookFactory();
        }
        return instance;
    }
    
    private static String key(Environment env, Action action, COMType comType){
        return new ActionKey( action,  env,  comType).toString();
    }
    
    private static String key(Environment env, Action action){
        return new ActionKey( action,  env).toString();
    }
    
    
    private PlaybookFactory(){}
}
