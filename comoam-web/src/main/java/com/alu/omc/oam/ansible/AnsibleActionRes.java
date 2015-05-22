package com.alu.omc.oam.ansible;

public class AnsibleActionRes
{
    private int code;
    private boolean succeed;
    private String message;
    public int getCode()
    {
        return code;
    }
    public void setCode(int code)
    {
        this.code = code;
    }
    public boolean isSucceed()
    {
        return succeed;
    }
    public void setSucceed(boolean succeed)
    {
        this.succeed = succeed;
    }
    public String getMessage()
    {
        return message;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }
}
