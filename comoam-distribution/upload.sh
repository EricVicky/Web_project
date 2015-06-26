#!/bin/sh
RPM=target/rpm/ELCM/RPMS/noarch/ELCM-0.7-1.noarch.rpm
scp $RPM combld@135.252.44.186:/local1/1360COM/ofc/FromFr/R5.0/OAM_Tool/
cksum $RPM >$RPM.cksum
scp $RPM.cksum combld@135.252.44.186:/local1/1360COM/ofc/FromFr/R5.0/OAM_Tool/
