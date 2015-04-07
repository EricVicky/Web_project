package com.alu.omc.oam.ansible;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.WorkspaceException;
import com.alu.omc.oam.config.COMConfig;

@Component
@Scope(value = "prototype")
public class Ansibleworkspace
{
    @Value("${ansible.workspace}")
    String workDirRoot;
    String workingDir;
    @Value("${ansible.log}")
    String logFileName;
    private static final Logger log = LoggerFactory.getLogger(PlaybookCall.class);
    public final static String        VAR_FILE_NAME   = "group_vars/all";
    public final static String        HOSTS_FILE_NAME = "hosts";
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
    
    public void init(COMConfig config){
        	log.info("Write var file to working directory...");
        try
        {
            FileUtils.writeStringToFile(new File(this.getWorkingdir().concat(VAR_FILE_NAME)), config.getVars());
            log.info("Write host file to working directory...");
            FileUtils.writeStringToFile(new File(this.getWorkingdir().concat(HOSTS_FILE_NAME)), config.getInventory().toInf()); 
            log.info("Copy ansible codes to working directory...");
            FileUtils.copyDirectory(new File(this.getWorkDirRoot() + "code"), new File(this.getWorkingdir()));
            FileUtils.writeStringToFile(new File(this.getWorkingdir() + "ansible.cfg"), 
                    config.getCfg().concat("\r\n").concat("log_path=" + this.getLogFile()));
            log.info("Write empty log file");
            FileUtils.write(this.getLogFile(), "-------Call Ansible......");
        }
        catch (Exception e)
        {
           throw new  WorkspaceException("failed to prepare workspace", e);
        }
    }
}
