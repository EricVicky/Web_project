package com.alu.omc.oam.ansible;

import java.io.File;
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
                    String.valueOf(new Date().getTime()))
                    .concat(File.separator);

        }
        return this.workingDir;
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
    }

    public File getLogFile()
    {
        return new File(this.getWorkingdir().concat(File.separator).concat(
                this.logFileName));
    }
}
