package com.alu.omc.oam.util;

import org.apache.commons.net.util.SubnetUtils;

public class NetworkUtil
{
 public static String getNetMask(String cidr){
     return new SubnetUtils(cidr).getInfo().getNetmask(); 
 }
 
 public static String getNetWorkPrefix(String cidr){
     return cidr.substring(cidr.indexOf('/')+1);
 }
}
