package com.alu.omc.oam.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.Ansibleworkspace;


public class OperationLog {

	private Action action;
	private String date;
	private String dir;
	private LogStatus logStatus;
	
	public OperationLog(){
		
	}
	public OperationLog(COMConfig config,Action action,String dir){
	    this.date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
	    this.action = action;
	    this.dir = dir;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
	public LogStatus getLogStatus() {
		return logStatus;
	}
	public void setLogStatus(LogStatus logStatus) {
		this.logStatus = logStatus;
	}

	
	

}
