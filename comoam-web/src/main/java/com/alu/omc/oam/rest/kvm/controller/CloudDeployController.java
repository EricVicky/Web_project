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
import com.alu.omc.oam.config.BACKUPConfig;
import com.alu.omc.oam.config.COMStack;
import com.alu.omc.oam.config.GRInstallConfig;
import com.alu.omc.oam.config.GRUnInstallConfig;
import com.alu.omc.oam.config.KVMCOMConfig;
import com.alu.omc.oam.config.OSCOMConfig;
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
    	ansibleDelegator.execute(Action.INSTALL, config );
        
    }
    @RequestMapping(value="/kvm/deployment", method=RequestMethod.POST)
    public void deploy( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.execute(Action.INSTALL, config );
    }
    
    @RequestMapping(value="/kvm/instances/{name}", method=RequestMethod.POST)
    public void deleteKVM( @RequestBody KVMCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.execute(Action.DELETE, config );
    }
    
    @RequestMapping(value="/os/instances/{name}", method=RequestMethod.POST)
    public void deleteOS( @RequestBody OSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.execute(Action.DELETE, config );
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
        ansibleDelegator.execute(Action.UPGRADE, config );
    }
    
    @RequestMapping(value="/os/upgrade", method=RequestMethod.POST)
    public void osupgrade( @RequestBody OSCOMConfig config) throws IOException, InterruptedException
    {
        ansibleDelegator.execute(Action.UPGRADE, config );
    }
    
    @RequestMapping(value="/kvm/backup", method=RequestMethod.POST)
    public void kvmbackup( @RequestBody BACKUPConfig<KVMCOMConfig> config) throws IOException, InterruptedException
    {
        ansibleDelegator.execute(Action.BACKUP, config );
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
        String vars = config.getVars();
        ansibleDelegator.execute(Action.GRINST_PRI, config);
        System.out.println("vars=" + vars);
    }   
    
    @RequestMapping(value="/gr/kvm/uninstall", method=RequestMethod.POST)
    public void install_gr(@RequestBody GRUnInstallConfig<KVMCOMConfig> config) 
    {
        String vars = config.getVars();
        ansibleDelegator.execute(Action.GRUNINST, config);
        System.out.println("vars=" + vars);
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
        ansibleDelegator.execute(Action.BACKUP, config );
    }



}
