package com.alu.omc.oam.util;

import java.util.Map;

import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class JsonYamlConverter 
{
	public static String convertYmal2Json(String yamlContent)
	{
		Yaml yaml = new Yaml();
		Map map = (Map) yaml.load(yamlContent);
		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
	
	public static String convertYmal2Json(Map yamlMap)
	{
		JSONObject jsonObject = new JSONObject(yamlMap);
		return jsonObject.toString();
	}
	
	public static String convertJson2Yaml(String jsonContent)
	{
		Yaml yaml = new Yaml();
		Map map = (Map)yaml.load(jsonContent);
		//System.out.println(yaml.dump(map));
		String yamlString = yaml.dump(map);
		return yamlString;
	}
	
	public static void main(String[] args) {
		//String abc = JsonYamlConverter.convertYmal2Json("os_settings: { os_username: { value: \"com-user1\", type: text } }");
		//JsonYamlConverter.convertJson2Yaml("aaa");
		//System.out.println(abc);
	}
}
