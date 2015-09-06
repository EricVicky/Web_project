#!/bin/sh
SSLDir=/opt/PlexView/ELCM/tmp
PKCS12File=/opt/PlexView/ELCM/tmp/ssl.crt/server.pkcs12
tomcatHomeDir=/opt/PlexView/ELCM/server
tomcatKeyStoreDir="$tomcatHomeDir/ssl/keystore"
CA_CRT="${SSLDir}/ssl.crt/ca.crt"
SERVER_CRT="${SSLDir}/ssl.crt/server.crt"
SERVER_KEY="${SSLDir}/ssl.key/server.key"

openssl pkcs12 -export -in ${SERVER_CRT} -inkey ${SERVER_KEY} -out ${PKCS12File} -name tomcat -CAfile ${CA_CRT} -passout pass:serverpkcs12
#test if the server.key exsit
if [ ! -f $SERVER_KEY ]; then
 echo "the server.key not found"
fi
#test if the CA certificate exisit
if [ ! -f $CA_CRT ]; then
 echo " the CA certificate not found"
fi

if [ ! -d $tomcatKeyStoreDir ]; then
   mkdir $tomcatKeyStoreDir
fi
if [  -f $tomcatKeyStoreDir/elcm.jks ]; then
	rm -f $tomcatKeyStoreDir/elcm.jks
fi
cd $SSLDir
if [ -f $PKCS12File ]; then
        if [ ! -d $tomcatKeyStoreDir ]; then
                mkdir -p $tomcatKeyStoreDir
        fi

        export RANDFILE=$tomcatKeyStoreDir/.rnd
        if [ -f "$tomcatKeyStoreDir/*.rnd" ]; then
                chmod  755  $tomcatKeyStoreDir/*.rnd
        fi

        echo "Convert Apache PKCS12 file to Java PKCS12 key store ..."
        openssl pkcs12 -in $PKCS12File -out server.pem -passin pass:serverpkcs12 -passout pass:serverpkcs12
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
