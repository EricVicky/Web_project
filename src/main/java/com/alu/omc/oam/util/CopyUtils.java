package com.alu.omc.oam.util;

import java.io.IOException;


public class CopyUtils {

	public static void copyFiles(String fromDir, String toDir) {
		CommandExec commandExec = new CommandExec("cp " + fromDir + " " + toDir);
        try {
			commandExec.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
