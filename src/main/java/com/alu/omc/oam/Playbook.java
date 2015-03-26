package com.alu.omc.oam;

public class Playbook
{
    public String playbookFileName;

    public String getFilePath(Ansibleworkspace workspance){
        return workspance.getWorkingdir().concat(playbookFileName);
    }
    public Playbook(String fileName){
       this.playbookFileName = fileName; 
    }
}
