package com.alu.omc.oam.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.kvm.model.Host;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
@Service
public class HostService
{
    
@Resource
private JsonDataSource dataSource;
private static Logger log = LoggerFactory.getLogger(WebsocketSender.class);

public List<String> imagelist(String host, String login, String dir) throws Exception{
    List<String> images = new ArrayList<String>();
    //it is for local test only
  	if(SystemUtils.IS_OS_WINDOWS){
    	images.add("rhel6.6_ora_client.qcow2");
    	images.add("rhel6.6_ora.qcow2");
		return images;  
  	}
  	if(Host.isLocalHost(host)){
  	    return getLocalImages(dir);
  	}

    String directory = dir;
    String privateKey = "/root/.ssh/id_rsa";

    java.util.Properties config = new java.util.Properties();
    config.put("StrictHostKeyChecking", "no");

    JSch ssh = new JSch();
    ssh.addIdentity(privateKey);
    Session session = ssh.getSession(login, host, 22);
    session.setConfig(config);
    session.connect();
    Channel channel = session.openChannel("sftp");
    channel.connect();

    ChannelSftp sftp = (ChannelSftp) channel;
    sftp.cd(directory);
    Vector<ChannelSftp.LsEntry> files = sftp.ls("*");
    System.out.printf("Found %d files in dir %s%n", files.size(), directory);

    for (ChannelSftp.LsEntry file : files) {
        if (file.getAttrs().isDir()) {
            continue;
        }
        log.info(file.getFilename());
        images.add(file.getFilename());
    }

    channel.disconnect();
    session.disconnect();
    return images;
}

public List<String> getLocalImages(String dir){
    File dirFile = new File(dir);
    String[] files = dirFile.list();
    List<String> images = new ArrayList();
    for(String file : files){
        if(file.contains("qcow2")){
          images.add(file);  
        }
    }
    return images;
}


    public List<Host> hostIPs(){
    	return dataSource.hosts();

    }
    
    public boolean ping(String host){
        JSch ssh = new JSch();
        final String PING_COMMAND = "ping -n 1 "; 
        boolean REACHABLE = false;
        try
        {
            Session session = ssh.getSession("127.0.0.1");
            ChannelExec channel= (ChannelExec)session.openChannel("shell");
            InputStream is = new ByteArrayInputStream(PING_COMMAND.concat(host).concat("\n").getBytes());
            channel.setInputStream(is);
            channel.setOutputStream(System.out);
            channel.connect(2*1000);
            channel.disconnect();
            session.disconnect();
            REACHABLE = (channel.getExitStatus() == 0);
            
        }
        catch (JSchException e)
        {
           log.error("failed to run ping command", e); 
        }
        return REACHABLE;
    }
    
}


