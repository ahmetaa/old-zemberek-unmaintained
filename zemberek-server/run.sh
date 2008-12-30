#!/bin/sh
source runbase.sh

case "$1" in
all)
mainclass="net.zemberekserver.server.ZemberekServer";
;;
dbus)
mainclass="net.zemberekserver.server.dbus.ZemberekDbus";
;;
socket)
mainclass="net.zemberekserver.server.socket.SocketServer";
;;
*)
exit 1;
;;
esac


java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp zemberek-server-${surum}.jar \
	-Djava.library.path=lib/$(get_jni_dir) -Xverify:none -Xms12m -Xmx14m ${mainclass}
