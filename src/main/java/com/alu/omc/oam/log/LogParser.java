package com.alu.omc.oam.log;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LogParser implements ILogParser
{
 private Stack<Step> steps = new Stack<Step>();
 private Step currentStep = null;
 private static final  Pattern taskPattern = Pattern.compile("^.*TASK:\\s\\[(.*)\\].*$");
 
 private Step nextStep(){
     return steps.pop();
 }
@Override
public ParseResult parse(String log)
{
     ParseResult res = new ParseResult();
     res.setLogMsg(log);
     Matcher m = taskPattern.matcher(log);
     if(m.find()){
         res.setTask(m.group(1));
     }
     if(currentStep != null){
         if(currentStep.expect(log)){
             res.setStep(currentStep.name);
             currentStep = nextStep();
         }else{
             res.setStep(currentStep.name);
         }
     }
     
     return res;
 }
 
 
 
 public LogParser(Map<String, String> stepDict){
     Iterator<String> it = stepDict.keySet().iterator();
     while(it.hasNext()){
         String keyword = it.next();
         String stepName = stepDict.get(keyword);
         steps.push(new Step(keyword, stepName));
     }

     currentStep = steps.pop();
 }
 
 public static void main(String[] args){
     String log = "2015-04-08 16:20:32,100 p=9383 u=root |  TASK: [installcom | install com]";
     Matcher m = taskPattern.matcher(log);
     if(m.find()){
         System.out.println(m.group(0));
     }
 }
 
 
 
class Step{
    private Pattern keywordPattern;
    private String name;
    
    public Step(String keyWord, String name){
        this.keywordPattern =  Pattern.compile(keyWord);
        this.name = name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    public boolean expect(String log){
       Matcher m = keywordPattern.matcher(log);
       return m.find();
    }
    
}




}
