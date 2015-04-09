package com.alu.omc.oam.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.ansible.Host;
import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
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
    	images.add("1111.qcow2");
    	images.add("2222.qcow2");
    	images.add("3333.qcow2");
		return images;  
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
        log.info("Reading file : %s%n", file.getFilename());
        BufferedReader bis = new BufferedReader(new InputStreamReader(sftp.get(file.getFilename())));
        String line = null;
        while ((line = bis.readLine()) != null) {
           log.info("image name=" + line); 
            images.add(line);
        }
        bis.close();
    }

    channel.disconnect();
    session.disconnect();
    return images;
}

    public List<Host> hostIPs(){
    	return dataSource.hosts();

    }
}
