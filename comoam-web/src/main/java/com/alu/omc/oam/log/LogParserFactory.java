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
        parserCache.put(new ActionKey(Action.UPGRADE, Environment.OPENSTACK), osUpgradeParser());
        parserCache.put(new ActionKey(Action.BACKUP, Environment.KVM), kvmBackupParser());
        parserCache.put(new ActionKey(Action.RESTORE, Environment.KVM), kvmRestoreParser());
        parserCache.put(new ActionKey(Action.INSTALL, Environment.OPENSTACK), osInstallParser());
        parserCache.put(new ActionKey(Action.DELETE, Environment.KVM), kvmDeleteParser());
        parserCache.put(new ActionKey(Action.DELETE, Environment.OPENSTACK), osDeleteParser());
        parserCache.put(new ActionKey(Action.BACKUP, Environment.OPENSTACK), osBackupParser());
        parserCache.put(new ActionKey(Action.RESTORE, Environment.OPENSTACK), osRestoreParser());
        parserCache.put(new ActionKey(Action.GRINST_PRI, Environment.KVM), kvmGrInstPriParser());
        parserCache.put(new ActionKey(Action.GRINST_SEC, Environment.KVM), kvmGrInstSecParser());
        parserCache.put(new ActionKey(Action.GRUNINST, Environment.KVM), kvmGrUnInstParser());
    }
    
    private ILogParser kvmGrInstPriParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[install\\sPRI\\sOAM\\sGR\\]", "Pri GR Install");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    private ILogParser kvmGrInstSecParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[install\\sSEC\\sDB\\sGR\\]", "Sec GR Install");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser kvmGrUnInstParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[Uninstall\\sOAM\\sGR\\]", "GR Uninstall");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser kvmDeleteParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK:\\s\\[vnf\\_destroy\\_vms\\s\\|\\sundefine\\soam\\svirtual\\smachine", "Undefine Virtual Machine");
        dict.put("TASK:\\s\\[vnf\\_destroy\\_vms\\s\\|\\sdestroy\\soam\\svirtual\\smachine", "Destroy Virtual Machine");
        dict.put("PLAY\\s\\[destroy", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser osDeleteParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("PLAY\\s\\[destroy", "Begin");
        return new LogParser(dict);
	}
    
    private ILogParser kvmBackupParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[backup\\_data\\s\\|\\sbackup\\sdata\\]", "Data Backup");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    private ILogParser kvmRestoreParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[restore\\_data\\s\\|\\screate\\slocal\\srestore\\sdirectory\\]", "Data Restore");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    private ILogParser osBackupParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[backup\\_data\\s\\|\\sbackup\\sdata\\]", "Data Backup");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    private ILogParser osRestoreParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[restore\\_data\\s\\|\\srestore\\sdata\\]", "Data Restore");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser osInstallParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("Reboot\\sserver", "Start COM");
        dict.put("cloud\\_init\\s\\|\\scloud\\sinit\\send", "Cloud Init");
        dict.put("deploy\\_stack\\s\\|\\scheck\\spresence\\sof\\sheat\\sstack", "check Presence of Heat stack");
        dict.put("stack\\_templates\\s\\|\\supdate\\sALU\\-1360\\-COM\\.hot\\.yaml\\sdocument", "Generate Heat Templates");
        dict.put("os\\_common\\s\\|\\svaliadtion\\skey", "valiadtion key");
        dict.put("os\\_common\\s\\|\\sRunning\\swith\\sOS\\scredentials", "Start");
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
        dict.put("Run\\s\\/install\\/scripts\\/post\\_image\\_replacement\\.sh", "Post Configuration");
        dict.put("change\\_kvm\\s\\|\\sCopy\\sqcow2\\sfiles\\sto\\sdirectories","Start VM Instance");
        dict.put("prepare\\s\\|\\sGenerate\\sdata\\ssource\\simage", "Generate Config Driver");
        dict.put("prepare\\s\\|\\sGenerate\\smeta-data", "Start");
        return new LogParser(dict);
    }
    private ILogParser kvmUpgradeParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("localhost", "Finished");
        dict.put("TASK:\\s\\[data_restore", "Data Restore");
        dict.put("post_install_populated", "Post Configuration");
        dict.put("post_image_replacement","Post Image Replacement");
        dict.put("TASK:\\s\\[data_backup", "Data Backup");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
    }   
    
    private ILogParser osUpgradeParser(){
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
