package com.alu.omc.oam.log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionKey;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.Environment;

@Component
public class LogParserFactory
{
    private final Map<ActionKey, ILogParser> parserCache = new HashMap<ActionKey, ILogParser>();
    
    public LogParserFactory(){
        parserCache.put(new ActionKey(Action.INSTALL, Environment.KVM), kvmInstallParser());
        parserCache.put(new ActionKey(Action.UPGRADE, Environment.KVM), kvmUpgradeParser());
        parserCache.put(new ActionKey(Action.BACKUP, Environment.KVM), kvmBackupParser());
        parserCache.put(new ActionKey(Action.INSTALL, Environment.OPENSTACK), osInstallParser());
    }
    
    private ILogParser kvmBackupParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("localhost", "Finished");
        dict.put("TASK:\\s\\[data_backup", "Data Backup");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser osInstallParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("Reboot\\sserver", "Finished");
        dict.put("post_image_replacement", "Post Configuration");
        dict.put("createkvm\\s\\|\\scopy\\sqcow2\\sfiles\\sto\\sdirectories", "Start VM Instance");
        dict.put("prepare\\s|\\sgenerate\\sdata\\ssource\\simage", "Generate Config Driver");
        dict.put("prepare\\s\\|\\sgenerate\\smeta-data", "Start");
        return new LogParser(dict);
    }

	public  ILogParser getLogParser(Action action, COMConfig config)
    {
        try
        {
            return parserCache.get(new ActionKey(action, config.getEnvironment())).clone();
        }
        catch (CloneNotSupportedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    private ILogParser kvmInstallParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("Reboot\\sserver", "Finished");
        dict.put("post_image_replacement", "Post Configuration");
        dict.put("createkvm\\s\\|\\scopy\\sqcow2\\sfiles\\sto\\sdirectories",
                "Start VM Instance");
        dict.put("prepare\\s|\\sgenerate\\sdata\\ssource\\simage", "Generate Config Driver");
        dict.put("prepare\\s\\|\\sgenerate\\smeta-data", "Start");
        return new LogParser(dict);
    }
    private ILogParser kvmUpgradeParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("localhost", "Finished");
        dict.put("TASK:\\s\\[data_restore", "Data Restore");
        dict.put("post_install_populated", "Post Configuration");
        dict.put("post_image_replacement",
                "Post Image Replacement");
        dict.put("TASK:\\s\\[data_backup", "Data Backup");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
    }   
    
}
