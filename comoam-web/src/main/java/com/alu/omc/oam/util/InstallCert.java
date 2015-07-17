package com.alu.omc.oam.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.environment.EnvironmentUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;


/**
 * Class used to add the server's certificate to the KeyStore with your trusted
 * certificates.
 */
public class InstallCert
{

    final static String[] REDHAD_KEYSTORE = new String[] {
            "/etc/pki/java/cacerts", "/etc/pki/ca-trust/extracted/java/cacerts" };
    final static String CERTIFICATE_PATH = "/opt/PlexView/ELCM/crt/openstack.crt";
    final static String DEFAULT_PASSWORD = "changeit";

    public static void main(String[] args) throws Exception
    {
        String host;
        int port;
        String passphrase;
        if ((args.length == 1) || (args.length == 2))
        {
            String[] c = args[0].split(":");
            host = c[0];
            port = (c.length == 1) ? 443 : Integer.parseInt(c[1]);
            passphrase = (args.length == 1) ? DEFAULT_PASSWORD : args[1];
        }
        else
        {
            System.out
                    .println("Usage: java InstallCert <host>[:port] [passphrase]");
            return;
        }

        new InstallCert().autoImport(host, port, passphrase);

    }

    public void autoImport(String host, int port, String passphrase)
    {
        try
        {
            saveCert(host, port, passphrase);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void autoImport(String host, int port)
    {
      this.autoImport(host, port, DEFAULT_PASSWORD); 
    }

    private final char[] HEXDIGITS = "0123456789abcdef".toCharArray();

    private String toHexString(byte[] bytes)
    {
        StringBuilder sb = new StringBuilder(bytes.length * 3);
        for (int b : bytes)
        {
            b &= 0xff;
            sb.append(HEXDIGITS[b >> 4]);
            sb.append(HEXDIGITS[b & 15]);
            sb.append(' ');
        }
        return sb.toString();
    }

    private static class SavingTrustManager implements X509TrustManager
    {

        private final X509TrustManager tm;
        private X509Certificate[]      chain;

        SavingTrustManager(X509TrustManager tm)
        {
            this.tm = tm;
        }

        public X509Certificate[] getAcceptedIssuers()
        {
            throw new UnsupportedOperationException();
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {
            throw new UnsupportedOperationException();
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException
        {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }

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

    public void saveCert(String host, int port, String passphrase)
            throws Exception
    {

        COMKeyStore[] kss = getKeyStore(passphrase);
        for (COMKeyStore ks : kss)
        {
            SSLContext context = SSLContext.getInstance("TLS");
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ks.keyStore());
            X509TrustManager defaultTrustManager = (X509TrustManager) tmf
                    .getTrustManagers()[0];
            SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
            context.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory factory = context.getSocketFactory();

            System.out.println("Opening connection to " + host + ":" + port
                    + "...");
            SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
            socket.setSoTimeout(10000);
            try
            {
                System.out.println("Starting SSL handshake...");
                socket.startHandshake();
                socket.close();
                System.out.println();
                System.out.println("No errors, certificate is already trusted");
            }
            catch (SSLException e)
            {
                System.out.println();
                e.printStackTrace(System.out);
            }

            X509Certificate[] chain = tm.chain;
            if (chain == null)
            {
                System.out.println("Could not obtain server certificate chain");
                return;
            }
            System.out.println();
            System.out.println("Server sent " + chain.length
                    + " certificate(s):");
            System.out.println();
            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            for (int i = 0; i < chain.length; i++)
            {
                X509Certificate cert = chain[i];
                System.out.println(" " + (i + 1) + " Subject "
                        + cert.getSubjectDN());
                System.out.println("   Issuer  " + cert.getIssuerDN());
                sha1.update(cert.getEncoded());
                System.out.println("   sha1    " + toHexString(sha1.digest()));
                md5.update(cert.getEncoded());
                System.out.println("   md5     " + toHexString(md5.digest()));
                System.out.println();
            }
            X509Certificate cert = chain[0];
            ks.setCertificateEntry(getAlias(host), cert);
            ks.store();
            ks.keytoolExport(getAlias(host), ks, passphrase, CERTIFICATE_PATH);
            System.out.println();
            System.out.println(cert);
            System.out.println();
            System.out .println("Added certificate to keystore " + ks.filePath +" using alias '"
                            + getAlias(host) + "'");
        }
    }

    public String getAlias(String host)
    {
        return host;
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

/*        public void exportCertificate(String alias, String targetPath)
        {
            try
            {
                Certificate cert = this.ks.getCertificate(alias);
                File file = new File(targetPath);
                System.out.println(cert.getPublicKey().toString());
                byte[] buf = cert.getEncoded();
                FileOutputStream os = new FileOutputStream(file);
                Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
                wr.write(new BASE64Encoder().encode(buf));
                os.write(buf);
                wr.flush();
                os.close();
            }
            catch (CertificateEncodingException | KeyStoreException
                    | IOException e)
            {
                e.printStackTrace();
            }
        }*/
        
        public void keytoolExport(String alias, COMKeyStore ks, String passphase, String targetPat) throws Exception{
           DefaultExecutor de =  new DefaultExecutor();
           HashMap envs = new HashMap();
           envs.putAll( EnvironmentUtils.getProcEnvironment());
           CommandLine cmdLine = CommandLine.parse(KeytoolExportComand(alias, ks, passphase, targetPat));
           de.execute(cmdLine, envs);
        }
        
        private String KeytoolExportComand(String alias, COMKeyStore ks, String passphase, String targetPath){
            StringBuffer command = new StringBuffer("keytool "); 
            List<String> paraList = new ArrayList<String>();
            paraList.add("-export");
            paraList.add("-alias ".concat(alias));
            paraList.add("-keystore ".concat("\"" + ks.filePath + "\""));
            paraList.add("-rfc ");
            paraList.add("-file ".concat(targetPath));
            command.append(StringUtils.join(paraList, " "));
            return command.toString();
        }
/*        public void exportPrivteKey(String alias, String targetPath) throws Exception{
            Key key = this.ks.getKey(alias, this.passphrase.toCharArray());
            if(key instanceof PrivateKey) {
                    BASE64Encoder encoder=new BASE64Encoder();
                    Certificate cert=this.ks.getCertificate(alias);
                    PublicKey publicKey=cert.getPublicKey();
                    KeyPair keyPair = new KeyPair(publicKey,(PrivateKey)key);
                    PrivateKey privateKey=keyPair.getPrivate();
                    String encoded=encoder.encode(privateKey.getEncoded());
                    FileWriter fw=new FileWriter(targetPath);
                    fw.write("—–BEGIN PRIVATE KEY—–\n");
                    fw.write(encoded);
                    fw.write("\n");
                    fw.write("—–END PRIVATE KEY—–");
                    fw.close();
            }
        }*/
    }

}