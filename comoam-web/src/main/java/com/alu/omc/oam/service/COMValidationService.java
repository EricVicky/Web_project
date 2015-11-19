package com.alu.omc.oam.service;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
@Service
public class COMValidationService {
	
	private String username = "axadmin";
	private String oamip ;
	private String password = "newsys";
	
	private Session getSession(String username, String oamip, String password){
        JSch shell = new JSch();
        Session session = null;
        try {
			session = shell.getSession(username, oamip, 22);
	        session.setPassword(password);
	        session.setConfig("StrictHostKeyChecking", "no");
	        session.connect(30000);
	        System.out.println("The session to COM server " + oamip + " is created");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return session;
	}
	
	private Session getSession(String ip){
        String privateKey = "/root/.ssh/id_rsa";
        
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        
        JSch ssh = new JSch();
        try
        {
            ssh.addIdentity(privateKey);
            Session session = ssh.getSession("root", ip, 22);
            return session;
        }
        catch (JSchException e)
        {
            e.printStackTrace();
        }
        return null;
	}
	
    private Channel getChannel(Session session){
    	Channel channel = null;
		try {
			channel = session.openChannel("shell");
			System.out.println("The channel is created");
			channel.connect(3*1000);
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return channel;
    }
    
    public String excuteShell(String command){
        Session session = null;
        if(SystemUtils.IS_OS_WINDOWS){
            session = getSession(this.username, this.oamip, this.password);
        }else{
            session = getSession(command);
        }
    	Channel channel = getChannel(session);
		String finalCommand = command + " \n";
		String string = null;
    	try {
    		OutputStream outstream = channel.getOutputStream();
    		InputStream in=channel.getInputStream();
			outstream.write(finalCommand.getBytes());
			outstream.flush();
			try{Thread.sleep(5000);}catch(Exception ee){}
			System.out.println("The command " + command + " is excuted");
			byte[] tmp=new byte[1024];
//            while(true){
                while(in.available()>0){
                  int i=in.read(tmp, 0, 1024);
                  if(i<0)break;
                  string = new String(tmp, 0, i);
                  System.out.print(string);
                }
//                if(channel.isClosed()){
//                  System.out.println("exit-status: "+channel.getExitStatus());
//                  break;
//                }
//                try{Thread.sleep(1000);}catch(Exception ee){}
//              }
            outstream.close();
            in.close();
            channel.disconnect();
            session.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return string;
    }
    
    public void setUserName( String username ){
    	this.username = username;
    }
    
    public void setoamip( String oamip ){
    	this.oamip = oamip;
    }
    
    public void setpassword( String password ){
    	this.password = password;
    }
    
    public boolean checkIfCOMProcessUp(String ip){
    	String resutlCheckCOM = excuteShell("CheckCOM");
    	System.out.println("" + resutlCheckCOM);
    	//String resultCheckInstallLog = excuteShell("/install/scripts/checkInstallLog.sh");
    	if(resutlCheckCOM.contains("Number of stopped process(es) : 0") ){
    		return true;
    	} else {
    		return false;
    	}
    }
    
/*	public static void main(String[] args) {
		// TODO Auto-generated method stub
		COMValidationService sessionToCOM = new COMValidationService();
		sessionToCOM.setoamip("10.223.1.85");
		sessionToCOM.setUserName("root");
		sessionToCOM.setpassword("newsys");
        boolean syl = sessionToCOM.checkCOM();
		System.out.println("" + syl);

	}*/

}
