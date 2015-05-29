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
import com.alu.omc.oam.ansible.persistence.JsonDataSource;
import com.alu.omc.oam.log.Loglistener;
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
    private JsonDataSource dataSource;


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
                        handler.onProcessComplete(paramInt);
                        try
                        {
                            Thread.currentThread().sleep(2*minterval);
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
                        handler.onProcessFailed(paramExecuteException);
                        try
                        {
                            Thread.currentThread().sleep(2*minterval);
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
    }
    
    @Override
    public Ansibleworkspace getWorkSpace()
    {
        return ansibleworkspace;
    }
}
