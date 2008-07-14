#!/bin/sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp zemberek-server-0.7.1.jar \
	-Djava.library.path=lib/native-$(arch) -Xverify:none -Xms12m -Xmx14m net.zemberekserver.server.ZemberekServer
