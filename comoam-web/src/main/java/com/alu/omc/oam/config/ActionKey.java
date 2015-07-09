package com.alu.omc.oam.config;

public class ActionKey
{
    private Action      action;
    private Environment env;
    private COMType comType;

    public ActionKey(Action action, Environment env)
    {
        this.action = action;
        this.env = env;
    }
    
    public ActionKey(Action action, Environment env, COMType comType)
    {
        this.action = action;
        this.env = env;
        this.comType = comType;
    }
    @Override
    public int hashCode(){
        return this.toString().hashCode();
    }
    
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if (obj instanceof ActionKey) {
            ActionKey ak = (ActionKey)obj;
            if(ak.action.equals(this.action) && ak.env.equals(this.env) 
            		&& ak.toString().equals(this.toString())){
                return true;
            }
        } 
        return false;
    }
    
    public String toString(){
    	if(this.comType != null && this.comType != COMType.FCAPS && this.comType != COMType.CM && this.comType != COMType.OAM){
    		return action.name().concat("_").concat(env.name().concat("_").concat(this.comType.name()));
    	}else{
    		return action.name().concat("_").concat(env.name());
    	}
    }

}
