package com.alu.omc.oam.config;

public class ActionKey
{
    private Action      action;
    private Environment env;

    public ActionKey(Action action, Environment env)
    {
        this.action = action;
        this.env = env;
    }
    @Override
    public int hashCode(){
        return new String(this.action.toString().concat("-")
                .concat(this.env.toString())).hashCode();
    }
    
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if (obj instanceof ActionKey) {
            ActionKey ak = (ActionKey)obj;
            if(ak.action.equals(this.action) && ak.env.equals(this.env)){
                return true;
            }
        } 
        return false;
    }
    
    public String toString(){
        return action.name().concat("_").concat(env.name());
    }

}
