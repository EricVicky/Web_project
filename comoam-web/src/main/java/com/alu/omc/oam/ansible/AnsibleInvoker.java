package com.alu.omc.oam.ansible;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.io.input.Tailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.exception.AnsibleException;
import com.alu.omc.oam.ansible.handler.IAnsibleHandler;
import com.alu.omc.oam.config.Action;
import com.alu.omc.oam.config.OperationLog;
import com.alu.omc.oam.log.Loglistener;
import com.alu.omc.oam.service.OperationLogService;
import com.alu.omc.oam.util.CommandProtype;
import com.alu.omc.oam.util.ICommandExec;

@Component("ansibleInvoker")
@Scope(value="prototype")
public class AnsibleInvoker implements IAnsibleInvoker
{
    @Resource
    private Ansibleworkspace ansibleworkspace;

    @Resource
    private  CommandProtype commandProtype;
    
    @Resource
    private OperationLogService operationLogService;


    private static Logger log = LoggerFactory.getLogger(AnsibleInvoker.class);

    public void invoke(final PlaybookCall pc, final IAnsibleHandler handler)
            throws AnsibleException
    {
        pc.prepare(ansibleworkspace);
        final long minterval = 1000L;
        final Tailer tailer = Tailer.create(this.getWorkSpace().getLogFile(),
                new Loglistener(handler), minterval, false);
        try
        {
            log.info("tail -f ".concat(this.getWorkSpace().getLogFile()
                    .getAbsolutePath()));
            File runDir = new File(ansibleworkspace.getRunDir());
            ICommandExec commandExe = commandProtype.create(pc, runDir);
            try
            {
                handler.onStart();
                commandExe.execute(new ExecuteResultHandler()
                {
                    @SuppressWarnings("static-access")
                    @Override
                    public void onProcessComplete(int paramInt)
                    {
                        try
                        {
                            Thread.currentThread().sleep(2*minterval);
                            handler.onProcessComplete(paramInt);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        tailer.stop();

                    }

                    @SuppressWarnings("static-access")
                    @Override
                    public void onProcessFailed(
                            ExecuteException paramExecuteException)
                    {
                        try
                        {
                            Thread.currentThread().sleep(2*minterval);
                            handler.onProcessFailed(paramExecuteException);
                        }
                        catch (InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        tailer.stop();
                    }

                });
            }
            catch (Exception e)
            {
                log.error("failed to call ansible", e);
                throw new AnsibleException("failed to execute command "
                        + pc.asCommand(), e);
            }
        }
        catch (Exception e)
        {
            log.error("failed to call ansible", e);
            handler.onError();
            tailer.stop();
            throw new AnsibleException("failed to call ansible", e);
        }
        doOperationLog();
    }
    
    public void doOperationLog(){
    	OperationLog operation = new OperationLog(ansibleworkspace.getConfig(),ansibleworkspace.getAction(),ansibleworkspace.getFolder());
    	if(ansibleworkspace.getAction()==Action.DELETE){
    		operationLogService.deleteLog(ansibleworkspace.getConfig().getStackName());
    	}else{
    		operationLogService.addLog(ansibleworkspace.getConfig().getStackName(),operation);
    	}
    }
    
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        return ansibleworkspace;
    }
}
