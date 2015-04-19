package com.alu.omc.oam.log;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.COMConfig;

@Component
public class LogParserFactory
{
    public  ILogParser getLogParser(Action action, COMConfig config)
    {
        Map<String, String> dict = new LinkedHashMap<String, String>();
        dict.put("Reboot\\sserver", "Finished");
        dict.put("post_image_replacement", "Post Configuration");
        dict.put("createkvm\\s\\|\\scopy\\sqcow2\\sfiles\\sto\\sdirectories",
                "Start VM Instance");
        dict.put("prepare\\s|\\sgenerate\\sdata\\ssource\\simage", "Generate Config Driver");
        dict.put("prepare\\s\\|\\sgenerate\\smeta-data", "Start");
        return new LogParser(dict);
    }
}
