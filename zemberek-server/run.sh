#!/bin/sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp lib/zemberek-cekirdek-2.0.jar:lib/zemberek-tr-2.0.jar:lib/dbus.jar:lib/unix.jar:lib/mina-core-1.1.0.jar:lib/slf4j-nop.jar:dist/zemberek_server-0.7.1.jar \
	-Djava.library.path=lib -Xverify:none -Xms12m -Xmx14m net.zemberekserver.server.ZemberekServer
