package com.alu.omc.oam.log;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogParser implements ILogParser 
{

 private static Logger logger = LoggerFactory.getLogger(LogParser.class);
 private Stack<Step> steps = new Stack<Step>();
 private Step currentStep = null;
 private static final  Pattern taskPattern = Pattern.compile("^.*TASK:\\s\\[(.*)\\].*$");
 
 private Step nextStep(){
     if(steps.size() >0){
         return steps.pop();
     }
     return null;
 }
@Override
public ParseResult parse(String log)
{
     ParseResult res = new ParseResult();
     res.setLogMsg(log);
     Matcher m = taskPattern.matcher(log);
     while(m.find()){
    	 res.setTask(m.group(1));
     }
//     if(m.find()){
//         res.setTask(m.group(1));
//     }
     if(currentStep != null){
         if(currentStep.expect(log)){
             logger.info("currentStep=" + currentStep.keywordPattern);
             logger.info("log=" + log);
             res.setStep(currentStep.name);
             currentStep = nextStep();
         }else{
             res.setStep(currentStep.name);
         }
     }
     
     return res;
 }

public ILogParser clone() throws CloneNotSupportedException{
   LogParser  parser =  new LogParser();
   parser.steps = (Stack<Step>)this.steps.clone();
   parser.currentStep = this.currentStep.clone();
   return parser;
}

public LogParser(){
    
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
 
 
 
 
class Step implements Cloneable {
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
    
    public Step clone () throws CloneNotSupportedException{
       return (Step)super.clone();
    }
    
    public boolean expect(String log){
       Matcher m = keywordPattern.matcher(log);
       return m.find();
    }
    
}




}
