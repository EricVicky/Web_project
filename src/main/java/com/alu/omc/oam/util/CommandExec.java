/* ------------------------------------------------------------------------------------------
 * Copyright (c) 2005 by Alcatel CIT. All rights reserver
 * ------------------------------------------------------------------------------------------
 * FILE                           : CommandExec
 * ------------------------------------------------------------------------------------------
 * DESCRIPTION              : 
 * CREATION DATE            : July, 2014 
 * AUTHOR                   : Tristan yu
 * 
 * PROJECT                  : OMC-CN
 * ------------------------------------------------------------------------------------------
 * CLASS
 *
 * ------------------------------------------------------------------------------------------
 * HISTORY
 * July, 2014      Tristan yu     Creation 
 * ------------------------------------------------------------------------------------------
 */
package com.alu.omc.oam.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class CommandExec implements ICommandExec {

    private Runtime runtime = Runtime.getRuntime();
    private String command;
    private File dir = null;
    private String[] envp = null;

    final static int INTERVAL = 5000;
    final static int COUNT = 360;
    private static Logger log = LoggerFactory.getLogger(CommandExec.class);

    public CommandExec(String command) {
        if (command == null) {
            throw new NullPointerException();
        }
        this.command = command;
    }

    public CommandExec(String command, String arg) {
        if (arg == null) {
            throw new NullPointerException();
        }
        this.command = command + " " + arg;
    }

    public CommandExec(String command, String[] args) {
        if (args == null) {
            this.command = command;
        } else {
            this.command = StringUtils.join(args, " ");
        }
    }

    public CommandExec(String command, String[] args, String[] envp) {
        this(command, args);
        this.envp = envp;
    }

    public CommandExec(String command, String[] args, String[] envp, File dir) {
        this(command, args);
        this.envp = envp;
        this.dir = dir;
    }

    public CommandResult execute() throws IOException, InterruptedException {

        Integer status = null;
        Process process;
        
        if (dir == null) {
            process = runtime.exec(command.toString(), envp);
        } else {
            process = runtime.exec(command.toString(), envp, dir);
        }
        

        if (process == null) {
            throw new IOException();
        }

        int i = 0;

        do {
            try {
                status = process.exitValue();
                break;
            } catch (IllegalThreadStateException e) {
                try {
                    Thread.sleep(INTERVAL);
                } catch (InterruptedException we) {

                }
            }
        } while (i++ < COUNT);

        if (status == null) {
            return new CommandResult(255, "Command executed timeout!");
        }
        if (status == 0) {
            return new CommandResult(status, commandOutput(process.getInputStream()));
        } else {
            return new CommandResult(status, commandOutput(process.getErrorStream()));
        }
    }

    private String commandOutput(InputStream is) throws IOException {
        BufferedReader streamInput = new BufferedReader(new InputStreamReader(is));

        StringBuffer result = new StringBuffer();
        String s;
        while ((s = streamInput.readLine()) != null) {
            result.append(s).append("\n");
        }
        return result.toString();
    }

    public static void main(String[] args) {/*
        // String[] myargs = {};
        // String[] evnp1 =
        // {"PATH=d:\\Program Files\\Java\\jdk1.7.0_60\\bin\\"};
        try {
            System.out.println(new CommandExec("d:\\Program Files\\Java\\jdk1.7.0_60\\bin\\java -version").execute());
        } catch (IOException | InterruptedException e Exception e) {
            e.printStackTrace();
        }
    */}

    @Override
    public void execute(ExecuteResultHandler handler) throws ExecuteException,
            IOException
    {
        // TODO Auto-generated method stub
        
    }
}
