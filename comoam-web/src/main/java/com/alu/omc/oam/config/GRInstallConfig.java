package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GRInstallConfig<T extends COMConfig> extends COMConfig implements Serializable
{
/**
      */
private static final long serialVersionUID = 3890963920968307212L;
private T pri;
private T sec;
private GRTrafic gr_traffic;
private boolean gr_install_active = true;
private IPtype gr_ip_type;
private Map<String, GRIP> gr_ip_config = new HashMap<String, GRIP>();
public GRTrafic getGr_traffic()
{
    return gr_traffic;
}
public void setGr_traffic(GRTrafic gr_traffic)
{
    this.gr_traffic = gr_traffic;
}
public boolean getGr_install_active()
{
    return gr_install_active;
}
public void setGr_install_active(boolean gr_install_active)
{
    this.gr_install_active = gr_install_active;
}
public IPtype getGr_ip_type()
{
    return gr_ip_type;
}
public void setGr_ip_type(IPtype gr_ip_type)
{
    this.gr_ip_type = gr_ip_type;
}
@JsonIgnore
@Override
public Inventory getInventory()
{
    Inventory inv;
    if(this.getGr_install_active()){
        inv = this.getPri().getInventory();
        inv.addNooamGroup();
    }else{
        inv =  this.getSec().getInventory();
    }
    return inv;
}
@JsonIgnore
@Override
public String getVars()
{
    Map<String, Object> vars = new HashMap<String, Object>();
    vars.put("gr_traffic", this.getGr_traffic());
    vars.put("vnf_type", this.getPri().getCOMType());
    vars.put("gr_ip_type", this.getGr_ip_type());
    vars.put("gr_install_active", this.getGr_install_active());
    Map<String, List<NIC>> actVMnicsDic =  ((NetworkConfig)this.getPri()).allInterface();
    Map<String, List<NIC>> stdVMnicsDic =  ((NetworkConfig)this.getSec()).allInterface();
    Iterator<String> it = actVMnicsDic.keySet().iterator();
    while(it.hasNext()){
        String vm = it.next();
        List<NIC> actVMnics = actVMnicsDic.get(vm);
        List<NIC> stdVMnics = stdVMnicsDic.get(vm);
        GRIP grip = new GRIP(actVMnics, stdVMnics, this.gr_ip_type == IPtype.ipv4
                ,this.getGr_install_active(), this.getGr_traffic());
        gr_ip_config.put(vm, grip);
    }
    vars.put("gr_ip_config", gr_ip_config);
    String json = Json2Object.object2Json(vars);
    return JsonYamlConverter.convertJson2Yaml(json);
}


@Override
@JsonIgnore
public Environment getEnvironment()
{
   return pri.getEnvironment();
}

public String getVnf_type(){
    return this.getCOMType().name();
}
@Override
@JsonIgnore
public COMType getCOMType()
{
    return pri.getCOMType();
}
public T getPri()
{
    return pri;
}
public void setPri(T pri)
{
    this.pri = pri;
}

public T getSec()
{
    return sec;
}
public void setSec(T sec)
{
    this.sec = sec;
}
@Override
@JsonIgnore
public String getStackName()
{
	if(this.getGr_install_active()){
		return pri.getStackName();
    }else{
    	return sec.getStackName();
    }
}

public class GRIP implements Serializable{

    private static final long serialVersionUID = -2898347056870265351L;
    private String pri_ip = "";
    private String sec_ip = "";
    private String red_ip = "";
    
    
    public GRIP(){
        
    }
    
    public GRIP(List<NIC> actNics, List<NIC> stbNics,  boolean isIpv4, boolean installActive, GRTrafic grTrafic){
        if(grTrafic == GRTrafic.SIMPlE){
            if(actNics.size() >0 && stbNics.size() > 0){
                simpleGR(actNics.get(0), stbNics.get(0), isIpv4);
            }
        }else if(grTrafic == GRTrafic.SEPARATION && actNics.size() >1 && stbNics.size() > 1){
                 sepGR(actNics, stbNics, isIpv4, installActive);
        }else if(grTrafic == GRTrafic.REDUDENCY && actNics.size() >2 && stbNics.size() > 2){
                 redGR(actNics, stbNics, isIpv4, installActive);
        }
    }
    
    private void simpleGR(NIC actnic, NIC stbnic, boolean isIPv4 ){
        this.pri_ip = actnic.getIpv4().getIpaddress();
        this.sec_ip = stbnic.getIpv4().getIpaddress();
    }
    
    private void sepGR(List<NIC> actNics, List<NIC> stbNics, boolean isIPv4, boolean installActive){
        if(installActive){
            this.pri_ip = isIPv4?actNics.get(0).getIpv4().ipaddress: 
                actNics.get(0).getIpv6().get(0).getIpaddress();
            this.sec_ip = isIPv4?stbNics.get(1).getIpv4().ipaddress: 
                stbNics.get(1).getIpv6().get(0).getIpaddress();
        }else{
            this.pri_ip = isIPv4?actNics.get(1).getIpv4().ipaddress: 
                actNics.get(1).getIpv6().get(0).getIpaddress();
            this.sec_ip = isIPv4?stbNics.get(0).getIpv4().ipaddress: 
                stbNics.get(0).getIpv6().get(0).getIpaddress();
            
        }       
    }
    
    private void redGR(List<NIC> actNics, List<NIC> stbNics, boolean isIPv4, boolean installActive){
        if(installActive){
            this.pri_ip = isIPv4?actNics.get(0).getIpv4().ipaddress: 
                actNics.get(0).getIpv6().get(0).getIpaddress();
            this.sec_ip = isIPv4?stbNics.get(1).getIpv4().ipaddress: 
                stbNics.get(1).getIpv6().get(0).getIpaddress();
            this.red_ip = isIPv4?actNics.get(2).getIpv4().ipaddress: 
                actNics.get(2).getIpv6().get(0).getIpaddress();
        }else{
            this.pri_ip = isIPv4?actNics.get(1).getIpv4().ipaddress: 
                actNics.get(1).getIpv6().get(0).getIpaddress();
            this.sec_ip = isIPv4?stbNics.get(0).getIpv4().ipaddress: 
                stbNics.get(0).getIpv6().get(0).getIpaddress();
            this.red_ip = isIPv4?stbNics.get(2).getIpv4().ipaddress: 
                stbNics.get(2).getIpv6().get(0).getIpaddress();
            
        } 
    }
    
    public String getPri_ip()
    {
        return pri_ip;
    }
    public void setPri_ip(String pri_ip)
    {
        this.pri_ip = pri_ip;
    }
    public String getSec_ip()
    {
        return sec_ip;
    }
    public void setSec_ip(String sec_ip)
    {
        this.sec_ip = sec_ip;
    }
    public String getRed_ip()
    {
        return red_ip;
    }
    public void setRed_ip(String red_ip)
    {
        this.red_ip = red_ip;
    }
    
    
}



}
