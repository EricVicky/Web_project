package com.alu.omc.oam.util;

public class YamlFormatterUtil {

	public static String format(String yaml) {
		return yaml.replaceAll("\\!\\!.*", "---");
	}
	
	public static void main(String [] args) {
		String str = "!!dajoifhiujijd\nadxhuadsdas";
		System.out.println("Before: " + str);
		System.out.println("Afteri: " + format(str));
		
	}
}
