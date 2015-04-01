package com.alu.omc.oam.ansible;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class Ansibleworkspace
{
    @Value("${ansible.workspace}")
    String workDirRoot;
    String workingDir = workDirRoot;
    @Value("${ansible.log}")
    String logFileName;

    public String getWorkingdir()
    {
        if (this.workingDir == null)
        {
            this.workingDir = workDirRoot.concat(
                    new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss-SSS")
                            .format(new Date())).concat(File.separator);

        }
        return this.workingDir;
    }

    public Ansibleworkspace(String workingDir, String logFile)
    {
        this.workingDir = workingDir;
        this.logFileName = logFile;
    }

    public String getWorkDirRoot()
    {
        return workDirRoot;
    }

    public Ansibleworkspace()
    {
    }
    
    public void setLogFile(){
        
    }

    public File getLogFile()
    {
        return new File(this.getWorkingdir().concat(File.separator).concat(
                this.logFileName));
    }
}
