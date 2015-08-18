package com.alu.omc.oam.ansible;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.WorkspaceException;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.Environment;
import com.alu.omc.oam.config.OperationLog;
import com.alu.omc.oam.service.COMStackService;

@Component
@Scope(value = "prototype")
public class Ansibleworkspace
{
	@Resource
    COMStackService service;
    @Value("${ansible.workspace}")
    String workDirRoot;
    @Value("${ansible.playbook}")
    String playbookDir;
    String workingDir;
    @Value("${ansible.log}")
    String logFileName;
    private static final Logger log = LoggerFactory.getLogger(Ansibleworkspace.class);
    public final static String        VAR_FILE_NAME   = "group_vars/all";
    public final static String        HOSTS_FILE_NAME = "inventory/hosts";
    private COMConfig config;
    private Action action;
    public String getWorkingdir()
    {
        if (this.workingDir == null)
        {
            this.workingDir = workDirRoot.concat(
                    new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")
                            .format(new Date())).concat("_")
                            .concat(config.getStackName())
                            .concat("_").concat(action.name())
                            .concat(File.separator);

        }
        return this.workingDir;
    }
    public String getFolder(){
    	return this.getWorkingdir().substring(workDirRoot.length());
    }
    
    public String getRunDir( ){
        return this.getWorkingdir().concat(Playbook.PLAYBOOK_DIR)
                .concat(File.separator)
                .concat(config.getEnvironment().name().toLowerCase())
                .concat(File.separator);
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
    
    public void init(COMConfig config, Action action){
        this.config = config;
        this.action = action;
        log.info("Write var file to working directory...");
        try
        {
            log.info("Copy ansible playbook to working directory...");
            final Path src= Paths.get(playbookDir);
            final Path dest = Paths.get(this.getWorkingdir());
            Files.walkFileTree(src, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                            throws IOException
                        {
                            Path targetdir = dest.resolve(src.relativize(dir));
                            try {
                                Files.copy(dir, targetdir, LinkOption.NOFOLLOW_LINKS);
                            } catch (FileAlreadyExistsException e) {
                                 if (!Files.isDirectory(targetdir))
                                     throw e;
                            }
                            return FileVisitResult.CONTINUE;
                        }
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException
                        {
                            Files.copy(file, dest.resolve(src.relativize(file)), LinkOption.NOFOLLOW_LINKS);
                            return FileVisitResult.CONTINUE;
                        }
                    });

            log.info("Write empty log file");
            FileUtils.writeStringToFile(new File(this.getWorkingdir().concat(VAR_FILE_NAME)), config.getVars());
            log.info("Write host file to working directory...");
            FileUtils.writeStringToFile(getHostFile(), config.getInventory().toInf()); 
            FileUtils.write(this.getLogFile(), "-------Call Ansible......\n");
        }
        catch (Exception e)
        {
           throw new  WorkspaceException("failed to prepare workspace", e);
        }
    }  
    
    private File getHostFile(){
        return new File(this.getWorkingdir().concat(HOSTS_FILE_NAME));
    }

	public COMConfig getConfig() {
		return config;
	}

	public Action getAction() {
		return action;
	}

	public COMStackService getService() {
		return service;
	}   
    
}
