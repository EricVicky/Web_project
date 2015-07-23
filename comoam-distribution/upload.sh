#!/bin/sh
RPM=$(find ./target/rpm/ELCM/RPMS/noarch/ -name ELCM*rpm)
TARGET=combld@135.252.44.186:/local1/1360COM/ofc/FromFr/R5.0/ELCM_Tool/
scp $RPM $TARGET
cksum $RPM >$RPM.cksum
scp $RPM.cksum $TARGET
