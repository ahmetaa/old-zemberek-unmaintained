#!/bin/sh
source runbase.sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp zemberek-server-${surum}.jar \
	-Djava.library.path=lib/$(get_jni_dir) -Xverify:none -Xms12m -Xmx14m net.zemberekserver.server.ZemberekServer
