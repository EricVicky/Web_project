package com.alu.omc.oam.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.alu.omc.oam.log.LogParser.Step;

public class ParseResult implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String            logMsg;
    private String            step;
    private List<String> task = new ArrayList<String>();
    private String result = "";
    public static final String SUCCEED = "succeed"; 
    public static final String FAILED = "failed"; 


    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLogMsg()
    {
        return logMsg;
    }

    public void setLogMsg(String logMsg)
    {
        this.logMsg = logMsg;
    }

    public String getStep()
    {
        return step;
    }

    public List<String> getTask() {
		return task;
	}

	public void setTask(String log) {
		task.add(log);
	}

	public void setStep(String step)
    {
        this.step = step;
    }

    public void ParseResult()
    {

    }
}
