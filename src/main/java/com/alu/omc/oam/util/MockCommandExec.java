/* ------------------------------------------------------------------------------------------
 * Copyright (c) 2005 by Alcatel CIT. All rights reserver
 * ------------------------------------------------------------------------------------------

 * ------------------------------------------------------------------------------------------
 */
package com.alu.omc.oam.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockCommandExec implements ICommandExec {

    private String action;
    private File workingDir = null;
    private final String SAMPLE_ANSIBLE_LOG_DIR ="/samplelog/";

    private static Logger log = LoggerFactory.getLogger(MockCommandExec.class);




    public MockCommandExec(String command, String[] args, String[] envp, File dir) {
        Pattern p = Pattern.compile("^.*\\\\([\\S]+)\\.yml");
        Matcher m = p.matcher(command);
        if(m.find()){
            action = m.group(1);
        }
        this.workingDir = dir;
    }

    public CommandResult execute() throws IOException, InterruptedException
    {

        try
        {
            URI uri = MockCommandExec.class
                    .getClassLoader()
                    .getResource(
                            SAMPLE_ANSIBLE_LOG_DIR.concat(action)
                                    .concat(".log")).toURI();
           File samplelog = new File(uri);
          LineIterator itertor = FileUtils.lineIterator(samplelog);
            File logFile = new File(this.workingDir.getAbsolutePath()
                    .concat(File.separator)
                    .concat("log.txt"));
           FileWriter fw = new FileWriter(logFile);
           Random r = new Random();
          while(itertor.hasNext()){
              String line = itertor.nextLine();
              if(line.length() == 0) continue;
              fw.write(line);
              fw.write(System.lineSeparator());
              fw.flush();
              int sleep =  r.nextInt(200)*5+500;
              Thread.currentThread().sleep(sleep);
          }
          fw.close();
           
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        

        return new CommandResult(0, "executed");
    }



    public static void main(String[] args) {
        Pattern p = Pattern.compile("^.*\\\\([\\S]+)\\.yml");
        Matcher m = p.matcher("ansible-playbook -i d:/tmp/ansible/2015-04-07_21-02-55-166\\hosts d:/tmp/ansible/2015-04-07_21-02-55-166\\install_kvm.yml");
       if(m.find()){
          System.out.println( m.group(1));
       } 
    }
}
