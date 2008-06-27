#!/bin/sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp lib/zemberek-cekirdek-2.1_rc1.jar:lib/zemberek-tr-2.1_rc1.jar:lib/dbus.jar:lib/unix.jar:lib/hexdump.jar:lib/mina-core-1.1.0.jar:lib/slf4j-nop.jar:zemberek-server-0.7.1_rc1.jar \
	-Djava.library.path=lib/native-$(arch) -Xverify:none -Xms12m -Xmx14m net.zemberekserver.server.ZemberekServer
