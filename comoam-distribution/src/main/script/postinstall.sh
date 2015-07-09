#!/bin/sh
echo "extract playbook"
tar -xf  /opt/PlexView/ELCM/playbook.tar -C /opt/PlexView/ELCM/

if [ -f /opt/PlexView/ELCM/datasource.tar.gz ]; then
    echo "restore data"
    tar -xzf /opt/PlexView/ELCM/datasource.tar.gz  -C / 
fi
echo "install jpam"
tar -xzf /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/lib/amd64/libjpam.so.tar.gz -C /usr/lib/jvm/jre-1.7.0-openjdk.x86_64/lib/amd64/
echo "start COM ELCM"
if [ ! -d /opt/PlexView/ELCM/server/logs ]; then
    mkdir /opt/PlexView/ELCM/server/logs
fi
if [ ! -d /opt/PlexView/ELCM/workspace ]; then
    mkdir /opt/PlexView/ELCM/workspace
fi

if [ ! -d /opt/PlexView/ELCM/ELCM-playbook/hot_files ]; then
    mkdir /opt/PlexView/ELCM/ELCM-playbook/hot_files 
fi
chmod +x /opt/PlexView/ELCM/server/bin/*sh
#key file generation and import
apachePKCS12File=/tmp/server.pkcs12
tomcatHomeDir=/opt/PlexView/ELCM/server
tomcatKeyStoreDir="$tomcatHomeDir/ssl/keystore"
if [ ! -f $tomcatKeyStoreDir/elcm.jks ]
then
        cd /tmp
        if [ -f $apachePKCS12File ]; then
                if [ ! -d $tomcatKeyStoreDir ]; then
                        mkdir -p $tomcatKeyStoreDir
                fi

                export RANDFILE=$tomcatKeyStoreDir/.rnd
                if [ -f "$tomcatKeyStoreDir/*.rnd" ]; then
                        chmod  755  $tomcatKeyStoreDir/*.rnd
                fi

                echo "Convert Apache PKCS12 file to Java PKCS12 key store ..."
                openssl pkcs12 -in $apachePKCS12File -out server.pem -passin pass:serverpkcs12 -passout pass:serverpkcs12
                openssl pkcs12 -export -in server.pem -out server.p12 -name "qosacert" -passin pass:serverpkcs12 -passout pass:serverpkcs12

                echo "Convert Java PKCS12 file to a JKS key store ..."
                keytool -importkeystore -srcstorepass serverpkcs12 -srckeystore server.p12 -destkeystore ELCM.jks -deststorepass serverpkcs12 -srcstoretype pkcs12 -deststoretype JKS

                echo "Remove file server.pem ..."
                rm -f server.pem echo "Remove file server.p12 ..."
                rm -f server.p12

                echo "Install Java Keystore file in Tomcat ..."
                mv -f ELCM.jks $tomcatKeyStoreDir

                echo "Tomcat keystore file elcm.jks has been created successfully!"
        else
                echo "Tomcat keystore file will not be created.";
        fi
else
        echo "Tomcat keystore file already exists, will not be created again.";
fi

/opt/PlexView/ELCM/script/migration-0.7-8.py

/opt/PlexView/ELCM/server/bin/startup.sh
bootrc=$(grep ELCM /etc/rc.local)
if [ -z "$bootrc" ]; then
    echo "/opt/PlexView/ELCM/server/bin/startup.sh" >>/etc/rc.local
fi
if [ ! -f ~/.ssh/id_rsa ]; then
    echo "generate ssh key"
    ssh-keygen -f ~/.ssh/id_rsa -t rsa -N '' -q
fi
echo 'Installation of ELCM tool completed!'
echo 'Access COM ELCM from the link https://HOST_IP_ADDRESS/ as axadmin user'


