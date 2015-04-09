package com.alu.omc.oam.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
@Service
public class HostService
{
    
private static Logger log = LoggerFactory.getLogger(WebsocketSender.class);

public List<String> imagelist(String host, String login, String dir) throws Exception{
    List<String> imgs = new ArrayList<String>();
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
            imgs.add(line);
        }
        bis.close();
    }

    channel.disconnect();
    session.disconnect();
    return imgs;
}
}
