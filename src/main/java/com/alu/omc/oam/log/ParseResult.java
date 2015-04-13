package com.alu.omc.oam.log;

import java.io.Serializable;

public class ParseResult implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String            logMsg;
    private String            task;
    private String            step;

    public String getLogMsg()
    {
        return logMsg;
    }

    public void setLogMsg(String logMsg)
    {
        this.logMsg = logMsg;
    }

    public String getTask()
    {
        return task;
    }

    public void setTask(String task)
    {
        this.task = task;
    }

    public String getStep()
    {
        return step;
    }

    public void setStep(String step)
    {
        this.step = step;
    }

    public void ParseResult()
    {

    }
}
