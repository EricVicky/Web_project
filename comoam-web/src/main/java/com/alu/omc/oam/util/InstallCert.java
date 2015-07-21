package com.alu.omc.oam.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.os.conf.OpenstackConfig;


/**
 * Class used to add the server's certificate to the KeyStore with your trusted
 * certificates.
 */
public class InstallCert
{

    final static String[] REDHAD_KEYSTORE = new String[] {
            "/etc/pki/java/cacerts", "/etc/pki/ca-trust/extracted/java/cacerts" };
    public final static String CERTIFICATE_PATH = "/opt/PlexView/ELCM/ELCM-playbook/crt/openstack.crt";
 //  public final static String CERTIFICATE_PATH = "d:\\openstack.crt";
    final static String DEFAULT_PASSWORD = "changeit";
    final static String OPENSTACK_AUTH= "openstack_auth";
    final static String CERT_TOOL_COMMAND = "keytool ";
	private static Logger log = LoggerFactory.getLogger(InstallCert.class);

    private COMKeyStore[] getKeyStore(String passphrase) throws Exception
    {
        String[] keystorePath = null;
        if (SystemUtils.IS_OS_WINDOWS)
        {
            char SEP = File.separatorChar;
            keystorePath = new String[] { System.getProperty("java.home")
                     + SEP + "lib" + SEP + "security" + SEP + "cacerts" };
        }
        else
        {
            keystorePath = REDHAD_KEYSTORE;

        }
        COMKeyStore[] ks = new COMKeyStore[keystorePath.length];
        for (int i = 0; i < keystorePath.length; i++)
        {
            ks[i] = new COMKeyStore(passphrase, keystorePath[i]);
        }
        return ks;
    }

    
     public void importCert()
            throws Exception
    {

        COMKeyStore[] kss = getKeyStore(DEFAULT_PASSWORD);
        for (COMKeyStore ks : kss)
        {
            ks.keytoolImport(OPENSTACK_AUTH, ks, DEFAULT_PASSWORD, CERTIFICATE_PATH );
        }
    }
     
    public void importCert(OpenstackConfig config) throws Exception{
        COMKeyStore[] kss = getKeyStore(DEFAULT_PASSWORD);
        String alias = config.getHost() == null? OPENSTACK_AUTH: OPENSTACK_AUTH.concat(config.getHost());
        for (COMKeyStore ks : kss)
        {
            try{
                ks.keytoolDelete(alias, ks, DEFAULT_PASSWORD, CERTIFICATE_PATH );
            }catch(Exception e){
               log.info("delete of certification ".concat(alias).concat(" failed! ")); 
            }
            ks.keytoolImport(alias, ks, DEFAULT_PASSWORD, CERTIFICATE_PATH );
        }
    } 
    

    public String getAlias(String host, int index)
    {
        index = index++;
        return host + "_" + index;
    }

    public class COMKeyStore
    {
        KeyStore ks;
        String   filePath;
        String   passphrase;

        public COMKeyStore(String passphrase, String keystorePath)
        {
            try
            {
                this.filePath = keystorePath;
                this.passphrase = passphrase;
                this.ks = KeyStore.getInstance(KeyStore.getDefaultType());
                File file = new File(keystorePath);
                InputStream in = new FileInputStream(file);
                System.out.println("Loading KeyStore " + file.getAbsolutePath() + "...");
                ks.load(in, passphrase.toCharArray());
                in.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void setCertificateEntry(String alias, Certificate cert)
        {
            try
            {
                this.ks.setCertificateEntry(alias, cert);
            }
            catch (KeyStoreException e)
            {
                e.printStackTrace();
            }
        }

        public void store()
        {
            try
            {
                OutputStream out = new FileOutputStream(this.filePath);
                this.ks.store(out, this.passphrase.toCharArray());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public KeyStore keyStore()
        {
            return this.ks;
        }

        
        public void keytoolImport(String alias, COMKeyStore ks, String passphase, String certPath) throws Exception{
           DefaultExecutor de =  new DefaultExecutor();
           HashMap envs = new HashMap();
           envs.putAll( EnvironmentUtils.getProcEnvironment());
           CommandLine cmdLine = CommandLine.parse(KeytoolImportComand(alias, ks, passphase, certPath));
           de.execute(cmdLine, envs);
        }
        
        public void keytoolDelete(String alias, COMKeyStore ks, String passphase, String targetPath) throws Exception{
           DefaultExecutor de =  new DefaultExecutor();
           HashMap envs = new HashMap();
           envs.putAll( EnvironmentUtils.getProcEnvironment());
           CommandLine cmdLine = CommandLine.parse(KeytoolDeleteComand(alias, ks, passphase));
           de.execute(cmdLine, envs);
        }
        
        private String KeytoolImportComand(String alias, COMKeyStore ks, String passphase, String certPath){
            StringBuffer command = new StringBuffer(CERT_TOOL_COMMAND); 
            List<String> paraList = new ArrayList<String>();
            paraList.add("-import -trustcacerts");
            paraList.add("-alias ".concat(alias));
            paraList.add("-keystore ".concat("\"" + ks.filePath + "\""));
            paraList.add("-storepass ".concat(passphase));
            paraList.add("-file ".concat(certPath));
            paraList.add("-noprompt");
            command.append(StringUtils.join(paraList, " "));
            return command.toString();
        }
        
         private String KeytoolDeleteComand(String alias, COMKeyStore ks, String passphase){
            StringBuffer command = new StringBuffer(CERT_TOOL_COMMAND); 
            List<String> paraList = new ArrayList<String>();
            paraList.add("-delete");
            paraList.add("-alias ".concat(alias));
            paraList.add("-keystore ".concat("\"" + ks.filePath + "\""));
            paraList.add("-storepass ".concat(passphase));
            paraList.add("-noprompt");
            command.append(StringUtils.join(paraList, " "));
            return command.toString();
        }
    }

}
