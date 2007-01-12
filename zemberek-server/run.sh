#!/bin/sh
java -cp lib/netty2.jar:lib/zemberek2-tr.jar:lib/dbus.jar:lib/unix.jar:lib/hexdump.jar -Djava.library.path=lib -Xverify:none -Xms12m -Xmx14m -jar zemberek_server-0.6.jar
