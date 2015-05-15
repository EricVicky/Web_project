package com.alu.omc.oam.ansible;

import java.io.File;


public class Playbook
{
    public String playbookFileName;
    public final static String PLAYBOOK_DIR = "playbooks";

    public String getFilePath(Ansibleworkspace workspance){
        return workspance.getRunDir().concat(File.separator).concat(playbookFileName);
    }
    public Playbook(String fileName){
       this.playbookFileName = fileName; 
    }
    
    public String getFileName(){
        return this.playbookFileName;
    }
    
}
