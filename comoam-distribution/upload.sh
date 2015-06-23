#!/bin/sh
RPM=target/rpm/ELCM/RPMS/noarch/ELCM-0.5-1.noarch.rpm
scp $RPM combld@135.251.236.66:/local1/1360COM/ofc/FromFr/R5.0/OAM_Tool/
cksum $RPM >$RPM.cksum
scp $RPM.cksum combld@135.251.236.66:/local1/1360COM/ofc/FromFr/R5.0/OAM_Tool/
