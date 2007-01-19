#!/bin/sh
java -cp lib/zemberek-cekirdek-2.0.jar:lib/zemberek-tr-2.0.jar:lib/dbus.jar:lib/unix.jar:lib/hexdump.jar:lib/mina-core-1.1pre.jar:lib/slf4j-api-1.1.0.jar:lib/slf4j-nop-1.1.0.jar:zemberek_server_dbus-0.6.jar:zemberek_server-0.6.jar -Djava.library.path=lib -Xverify:none -Xms12m -Xmx14m net.zemberekserver.server.ZemberekServer
