package com.alu.omc.oam;

import java.io.File;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Ansibleworkspace
{
    final String workDirRoot = "/home/ansible/";
    String       workingDir  = workDirRoot;
    String       logFileName = "log.txt";

    public String getWorkingdir()
    {
        return workingDir;
    }

    public Ansibleworkspace(String workingDir)
    {
        this.workingDir = workingDir;
    }

    public String getWorkDirRoot()
    {
        return workDirRoot;
    }

    public Ansibleworkspace()
    {
        this.workingDir = workDirRoot.concat(
                String.valueOf(new Date().getTime())).concat(File.separator);
    }

    public File getLogFile()
    {
        return new File(this.workingDir.concat(File.separator).concat(
                this.logFileName));
    }
}
