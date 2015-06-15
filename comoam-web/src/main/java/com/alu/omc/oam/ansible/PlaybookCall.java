package com.alu.omc.oam.ansible;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.ansible.exception.WorkspaceException;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;

public class PlaybookCall implements AnsibleCall
{
    /**
     * @Fields playbook : the file name of playbook
     */
    private Playbook            playbook;
    private static Logger       log             = LoggerFactory
                                                        .getLogger(PlaybookCall.class);
    private COMConfig           config;
    private Action              action;
    private static final String ANSIBLE_COMMAND = "ansible-playbook -c paramiko ";

    /**
     * @Fields playbook : the file name of playbook
     */

    public PlaybookCall(COMConfig config, Action action)
    {
        this.playbook = PlaybookFactory.getInstance().getPlaybook(action,
                config);
        this.config = config;
        this.action = action;
    }

    public void prepare(Ansibleworkspace space)
    {
        try
        {
            space.init(config);
        }
        catch (WorkspaceException e)
        {
            e.printStackTrace();
        }
    }
    
    public String asCommand(){
        return ANSIBLE_COMMAND.concat("-i ").concat( "../../")
                .concat(Ansibleworkspace.HOSTS_FILE_NAME).concat(" ").concat(this.playbook.getFileName());
    }

    public COMConfig getConfig()
    {
        return config;
    }

    public Action getAction()
    {
        return this.action;
    }
}
