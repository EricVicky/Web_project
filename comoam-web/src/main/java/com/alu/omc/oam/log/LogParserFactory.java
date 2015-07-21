package com.alu.omc.oam.log;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ActionKey;
import com.alu.omc.oam.config.COMConfig;
import com.alu.omc.oam.config.COMType;
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
        parserCache.put(new ActionKey(Action.GRINST_PRI, Environment.OPENSTACK), osGrInstPriParser());
        parserCache.put(new ActionKey(Action.GRINST_SEC, Environment.OPENSTACK), osGrInstSecParser());
        parserCache.put(new ActionKey(Action.GRUNINST, Environment.OPENSTACK), osGrUnInstParser());
        parserCache.put(new ActionKey(Action.INSTALL, Environment.KVM, COMType.HPSIM), kvmovmInstallParser());
        parserCache.put(new ActionKey(Action.INSTALL, Environment.KVM, COMType.ATC), kvmovmInstallParser());
        parserCache.put(new ActionKey(Action.DELETE, Environment.KVM, COMType.ATC), kvmovmDeleteParser());
        parserCache.put(new ActionKey(Action.UPGRADE, Environment.KVM, COMType.ATC), kvmovmUpgradeParser());
        parserCache.put(new ActionKey(Action.INSTALL, Environment.KVM, COMType.QOSAC), kvmqosacInstallParser());
        parserCache.put(new ActionKey(Action.DELETE, Environment.KVM, COMType.QOSAC), kvmQosacDeleteParser());
        parserCache.put(new ActionKey(Action.UPGRADE, Environment.KVM, COMType.HPSIM), kvmovmUpgradeParser());
        parserCache.put(new ActionKey(Action.DELETE, Environment.KVM, COMType.HPSIM), kvmovmDeleteParser());
        parserCache.put(new ActionKey(Action.UPGRADE, Environment.KVM, COMType.QOSAC), kvmqosacUpgradeParser());
        parserCache.put(new ActionKey(Action.BACKUP, Environment.KVM, COMType.QOSAC), kvmqosacBackupParser());
        parserCache.put(new ActionKey(Action.RESTORE, Environment.KVM, COMType.QOSAC), kvmqosacRestoreParser());        
        parserCache.put(new ActionKey(Action.INSTALL, Environment.KVM, COMType.ARS), kvmarsInstallParser());
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
    
    private ILogParser osGrInstPriParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[install\\sPRI\\sOAM\\sGR\\]", "Pri GR Install");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    private ILogParser osGrInstSecParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[install\\sSEC\\sDB\\sGR\\]", "Sec GR Install");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser osGrUnInstParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[Uninstall\\sOAM\\sGR\\]", "GR Uninstall");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser kvmDeleteParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[vnf\\_delete\\_vms\\s\\|\\sundefine\\svirtual\\smachine\\]", "Undefine Virtual Machine");
        dict.put("TASK\\:\\s\\[vnf\\_delete\\_vms\\s\\|\\sdestroy\\svirtual\\smachine\\]", "Destroy Virtual Machine");
        dict.put("PLAY\\s\\[destroy\\sall\\svirtual\\smachines\\]", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser osDeleteParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("stack-delete", "Destroy stack");
        dict.put("check\\spresence\\sof\\sstack", "Check Presence of stack");
        dict.put("Running\\swith\\sOS\\scredentials", "Start");
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
        dict.put("TASK\\:\\s\\[Reboot\\sserver\\]", "Finished");
        dict.put("TASK\\:\\s\\[run\\spost\\sreplace\\sscript\\,\\smay\\stake\\saround\\s20\\sminutes\\]", "Start COM");
        dict.put("wait\\sfor\\svirtual\\smachines\\sto\\sbe\\salive", "Cloud Init");
        dict.put("TASK\\:\\s\\[deploy\\_stack\\s\\|\\scheck\\spresence\\sof\\sheat\\sstack\\]", "Check Presence of Heat Stack");
        dict.put("TASK\\:\\s\\[stack\\_templates\\s\\|\\sRunning\\swith\\sthe\\sfollowing\\soptions\\]", "Generate Heat Templates");
        dict.put("TASK\\:\\s\\[os\\_common\\s\\|\\svaliadtion\\skey\\]", "Valiadtion Key");
        dict.put("TASK\\:\\s\\[os\\_common\\s\\|\\sRunning\\swith\\sOS\\scredentials\\]", "Start");
        return new LogParser(dict);
    }

	public  ILogParser getLogParser(Action action, COMConfig config)
    {
        try
        {
            return parserCache.get(new ActionKey(action, config.getEnvironment(), config.getCOMType())).clone();
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
        dict.put("TASK\\:\\s\\[Reboot\\sserver\\]", "Finished");
        dict.put("PLAY\\s\\[image\\sreplacement\\spost\\sscript\\]", "Prepare Install Options");
        dict.put("TASK\\:\\s\\[vnf\\_create\\_vms\\s\\|\\screate\\svirtual\\smachine\\sinstance\\]","Start VM Instance");
        dict.put("TASK\\:\\s\\[vnf\\_create\\_disk\\s\\|\\screate\\sdata\\sdisk\\simage\\]", "Generate Config Driver");
        dict.put("PLAY\\s\\[prepare\\sdata\\sfor\\svirtual\\smachines\\]", "Start");
        return new LogParser(dict);
    }
    private ILogParser kvmUpgradeParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("TASK\\:\\s\\[Reboot\\sserver\\]", "Finished");
        dict.put("TASK\\:\\s\\[restore\\_data\\s\\|\\srestore\\sdata\\]", "Data Restore");
        dict.put("TASK\\:\\s\\[vnf\\_create\\_vms\\s\\|\\scopy\\sqcow2\\sdisk\\simage\\]", "Post Image Replacement");
        dict.put("TASK\\:\\s\\[vnf\\_prepare\\_vms\\s\\|\\screate\\sdata\\sdirectory\\sfor\\svirtual\\smachine\\]","Prepare Virtual Machines");
        dict.put("TASK\\:\\s\\[backup\\_data\\s\\|\\screate\\slocal\\sbackup\\sdirectory\\]", "Data Backup");
        dict.put("PLAY\\s\\[stop\\sCOM\\]", "Start");
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
    
    private ILogParser kvmovmInstallParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put(".*post_install.*", "Post Configuration");
        dict.put("change\\_kvm\\s\\|\\sCopy\\sqcow2\\sfiles\\sto\\sdirectories","Start VM Instance");
        dict.put("prepare\\s\\|\\sGenerate\\sdata\\ssource\\simage", "Generate Config Driver");
        dict.put("prepare\\s\\|\\sGenerate\\smeta-data", "Start");
        return new LogParser(dict);
    }

    private ILogParser kvmqosacInstallParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("TASK\\:\\s\\[Reboot\\sserver\\]", "Finished");
        dict.put("may\\stake\\saround\\s20\\sminutes", "Post Configuration");
        dict.put("change\\_kvm\\s\\|\\sCopy\\sqcow2\\sfiles\\sto\\sdirectories","Start VM Instance");
        dict.put("prepare\\s\\|\\sGenerate\\sdata\\ssource\\simage", "Generate Config Driver");
        dict.put("PLAY\\s\\[Auto\\sinstall\\scom\\son\\skvm\\]", "Start");
        return new LogParser(dict);
    }

    private ILogParser kvmovmUpgradeParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("wait_for_server_start","Start Virtual Machines");
        dict.put("Auto\\sinstall\\scom\\son\\skvm", "Data Backup");
        dict.put("TASK\\:\\s\\[hpsim", "Start");
        return new LogParser(dict);
    }
    
    private ILogParser kvmovmDeleteParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[vnf\\_delete\\_vmfiles\\s\\|\\sdelete\\svirtual\\smachine\\sfiles\\]", "Delete Virtual Machine Files");     
        dict.put("TASK\\:\\s\\[vnf\\_delete\\_vms\\s\\|\\sundefine\\svirtual\\smachine\\]","Undefine Virtual Machines");
        dict.put("PLAY\\s\\[destroy\\sall\\svirtual\\smachines\\]", "Destroy Virtual Machines");
        dict.put("\\/usr\\/bin\\/", "Start");
        return new LogParser(dict);
    }
    private ILogParser kvmQosacDeleteParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[vnf\\_delete\\_vmfiles\\s\\|\\sdelete\\svirtual\\smachine\\sfiles\\]", "Delete Virtual Machine Files");     
        dict.put("TASK\\:\\s\\[vnf\\_delete\\_vms\\s\\|\\sundefine\\svirtual\\smachine\\]","Undefine Virtual Machines");
        dict.put("PLAY\\s\\[destroy\\sall\\svirtual\\smachines\\]", "Destroy Virtual Machines");
        dict.put("\\/usr\\/bin\\/", "Start");
        return new LogParser(dict);
    }
    private ILogParser kvmqosacUpgradeParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("TASK\\:\\s\\[Reboot\\sserver\\]", "Finished");
        dict.put("PLAY\\s\\[restore\\sdata\\]", "Data Restore");
        dict.put("PLAY\\s\\[image\\sreplacement\\spost\\sscript\\]", "Post Image Replacement");
        dict.put("PLAY\\s\\[prepare\\sdata\\sfor\\svirtual\\smachines\\]","Prepare Virtual Machines");
        dict.put("PLAY\\s\\[backup\\scom\\sdata\\]", "Data Backup");
        dict.put("PLAY\\s\\[stop\\sCOM\\]", "Start");
        return new LogParser(dict);
    }
    private ILogParser kvmqosacBackupParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
    	dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[backup\\_data\\s\\|\\sbackup\\sdata\\]", "Data Backup");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    private ILogParser kvmqosacRestoreParser() {
    	Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[restore\\_data\\s\\|\\screate\\slocal\\srestore\\sdirectory\\]", "Data Restore");
        dict.put("ansible-playbook", "Start");
        return new LogParser(dict);
	}
    
    private ILogParser kvmarsInstallParser(){
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("PLAY\\sRECAP", "Finished");
        dict.put("TASK\\:\\s\\[execute\\sinstall_boe_linux.sh","Install");
        dict.put("TASK\\:\\s\\[create\\sdirectory", "Prepare Environment");
        dict.put("PLAY\\s\\[Install\\sARS", "Start");
        return new LogParser(dict);
    }
    
}
