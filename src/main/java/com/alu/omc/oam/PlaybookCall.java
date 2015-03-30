package com.alu.omc.oam;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class PlaybookCall
{
/**
  * @Fields playbook : the file name of playbook 
  */
private Playbook playbook;
private String parameter;
private Inventory inventory;
private String vars;
private final String VAR_FILE_NAME = "group_vars/all";
private final String HOSTS_FILE_NAME = "hosts";

public PlaybookCall(COMConfig config, Action action){
   this.inventory = config.getInventory();
   this.vars = config.getVars();
   this.playbook = PlaybookFactory.getInstance().getPlaybook(action, config);
}
public String prepare(Ansibleworkspace space){
    try
    {
        FileUtils.writeStringToFile(new File(space.getWorkingdir().concat(VAR_FILE_NAME)), this.vars);
        FileUtils.writeStringToFile(new File(space.getWorkingdir().concat(HOSTS_FILE_NAME)),this.inventory.toInf()); 
    }
    catch (IOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return null;
    }
    return "-i " + space.getWorkingdir() + HOSTS_FILE_NAME + " --tags prepare " + this.playbook.getFilePath(space);
}

}
