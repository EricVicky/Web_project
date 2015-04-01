package com.alu.omc.oam.ansible;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.CopyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;

public class PlaybookCall
{
/**
  * @Fields playbook : the file name of playbook 
  */
private Playbook playbook;
private String parameter;
private Inventory inventory;
private String vars;
private String cfg;
private final String VAR_FILE_NAME = "group_vars/all";
private final String HOSTS_FILE_NAME = "hosts";
private static final Log log = LogFactory.getLog(PlaybookCall.class);

public PlaybookCall(COMConfig config, Action action){
   this.inventory = config.getInventory();
   this.vars = config.getVars();
   this.cfg = config.getCfg();
   this.playbook = PlaybookFactory.getInstance().getPlaybook(action, config);
}
public String prepare(Ansibleworkspace space){
    try
    {
    	log.info("Write var file to working directory...");
        FileUtils.writeStringToFile(new File(space.getWorkingdir().concat(VAR_FILE_NAME)), this.vars);
        log.info("Write host file to working directory...");
        FileUtils.writeStringToFile(new File(space.getWorkingdir().concat(HOSTS_FILE_NAME)),this.inventory.toInf()); 
        log.info("Copy ansible codes to working directory...");
        FileUtils.copyDirectory(new File(space.getWorkDirRoot() + "code"), new File(space.getWorkingdir()));
        FileUtils.writeStringToFile(new File(space.getWorkingdir() + "ansible.cfg"), 
        		cfg.concat("\r\n").concat("log_path=" + space.getLogFile()));
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
