package com.alu.omc.oam.rest.kvm.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alu.omc.oam.ansible.AnsibleDelegator;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.ArsCOMConfig;
import com.alu.omc.oam.config.AtcCOMConfig;
import com.alu.omc.oam.config.AtcOSCOMConfig;
import com.alu.omc.oam.config.BACKUPConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.GRUnInstallConfig;
import com.alu.omc.oam.config.HpsimCOMConfig;
import com.alu.omc.oam.config.HpsimOSCOMConfig;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
import com.alu.omc.oam.config.QosacCOMConfig;
import com.alu.omc.oam.config.QosacOSCOMConfig;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.service.COMStackService;
import com.alu.omc.oam.service.HostService;

@RestController 
public class CloudDeployController 
{
    @Resource
    private AnsibleDelegator ansibleDelegator;
    @Resource
    private HostService hostService;
    @Resource
    COMStackService cOMStackService;
    
    @RequestMapping(value="/os/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody OSCOMConfig config) throws IOException, InterruptedException
    {
    	ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
        
    }
    @RequestMapping(value="/kvm/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }

    @RequestMapping(value="/ovm/HPSIMdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody HpsimCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/os/ovm/HPSIMdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody HpsimOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/os/ovm/QOSACdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody QosacOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/os/ovm/ATCdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody AtcOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/os/ovm/QOSACdelete", method=RequestMethod.POST)
    public void delete( @RequestBody QosacOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/os/ovm/HPSIMdelete", method=RequestMethod.POST)
    public void delete( @RequestBody HpsimOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/os/ovm/ATCdelete", method=RequestMethod.POST)
    public void delete( @RequestBody AtcOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/ovm/ATCdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody AtcCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/ovm/QOSACdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody QosacCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/ovm/ARSdeployment", method=RequestMethod.POST)
    public void deploy( @RequestBody ArsCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.INSTALL, config );
    }
 

    @RequestMapping(value="/ovm/HPSIMupgrade", method=RequestMethod.POST)
    public void upgrade( @RequestBody HpsimCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.UPGRADE, config );
    }

    @RequestMapping(value="/ovm/QOSACupgrade", method=RequestMethod.POST)
    public void upgrade( @RequestBody QosacCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.UPGRADE, config );
    }
    
    @RequestMapping(value="/os/ovm/QOSACupgrade", method=RequestMethod.POST)
    public void upgrade( @RequestBody QosacOSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.UPGRADE, config );
    }
    
    @RequestMapping(value="/kvm/instances/QOSAC{name}", method=RequestMethod.POST)
    public void deleteKVM( @RequestBody QosacCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/kvm/instances/HPSIM{name}", method=RequestMethod.POST)
    public void deleteKVM( @RequestBody HpsimCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }

    @RequestMapping(value="/kvm/instances/ATC{name}", method=RequestMethod.POST)
    public void deleteKVM( @RequestBody AtcCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/kvm/instances/{name}", method=RequestMethod.POST)
    public void deleteKVM( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/os/instances/{name}", method=RequestMethod.POST)
    public void deleteOS( @RequestBody OSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.DELETE, config );
    }
    
    @RequestMapping(value="/kvm/images", method=RequestMethod.GET)
    public List<String> images(@ModelAttribute("host") String host, @ModelAttribute("dir") String dir)  
    {
        try
        {
            return hostService.imagelist(host,"root", dir );
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @RequestMapping(value="/kvm/upgrade", method=RequestMethod.POST)
    public void upgrade( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.UPGRADE, config );
    }
    
    @RequestMapping(value="/os/upgrade", method=RequestMethod.POST)
    public void osupgrade( @RequestBody OSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.UPGRADE, config );
    }
    
    @RequestMapping(value="/kvm/backup", method=RequestMethod.POST)
    public void kvmbackup( @RequestBody BACKUPConfig<KVMCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.BACKUP, config );
    }
    @RequestMapping(value="/kvm/restore", method=RequestMethod.POST)
    public void kvmrestore( @RequestBody BACKUPConfig<KVMCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.RESTORE, config );
    }
    
    @RequestMapping(value="/kvm/instances", method=RequestMethod.GET)
    public List<COMStack>  kvminstances() throws IOException, InterruptedException
    {
    	List<COMStack> instances = cOMStackService.list();
    	return instances;
    }
    @RequestMapping(value="/instances", method=RequestMethod.GET)
    public List<COMStack>  allinstances() throws IOException, InterruptedException
    {
    	List<COMStack> instances = cOMStackService.list();
    	return instances;
    }
    @RequestMapping(value="/gr/kvm/install", method=RequestMethod.POST)
    public void install_gr(@RequestBody GRInstallConfig<KVMCOMConfig> config) 
    {
    	if(config.getGr_install_active()==true){
    		ansibleDelegator.addAnsibleTask(Action.GRINST_PRI, config);
    	}else{
    		ansibleDelegator.addAnsibleTask(Action.GRINST_SEC, config);
    	}
    }   
    
        @RequestMapping(value="/gr/os/install", method=RequestMethod.POST)
    public void install_os_gr(@RequestBody GRInstallConfig<OSCOMConfig> config) 
    {
    	if(config.getGr_install_active()==true){
    		ansibleDelegator.addAnsibleTask(Action.GRINST_PRI, config);
    	}else{
    		ansibleDelegator.addAnsibleTask(Action.GRINST_SEC, config);
    	}
    }  
    
    @RequestMapping(value="/gr/kvm/uninstall", method=RequestMethod.POST)
    public void uninstall_kvm_gr(@RequestBody GRUnInstallConfig<KVMCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.GRUNINST, config);
    } 
    
    @RequestMapping(value="/gr/os/uninstall", method=RequestMethod.POST)
    public void uninstall_os_gr(@RequestBody GRUnInstallConfig<OSCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.GRUNINST, config);
    } 
    
    @RequestMapping(value="/os/instances", method=RequestMethod.GET)
    public List<COMStack>  osinstances() throws IOException, InterruptedException
    {
    	List<COMStack> instances = cOMStackService.list();
    	return instances;
    }

    
    @RequestMapping(value="/kvm/hostips", method=RequestMethod.GET)
    public List<Host>  achostips() throws IOException, InterruptedException
    {
    	List<Host> achostips = hostService.hostIPs();
    	return achostips;
    }
    
    @RequestMapping(value="/os/backup", method=RequestMethod.POST)
    public void osbackup( @RequestBody BACKUPConfig<OSCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.BACKUP, config );
    }

    @RequestMapping(value="/os/restore", method=RequestMethod.POST)
    public void osrestore( @RequestBody BACKUPConfig<OSCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.addAnsibleTask(Action.RESTORE, config );
    }
    @RequestMapping(value="/ansible/task", method=RequestMethod.GET)
    public void executetask( @ModelAttribute("comStack") String comStack) 
    {
        ansibleDelegator.execute(comStack);
    }
}
