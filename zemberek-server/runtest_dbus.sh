#!/bin/sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp .:lib/dbus.jar:lib/unix.jar:lib/hexdump.jar:dist/zemberek-server-0.7.1.jar \
	-Djava.library.path=lib/native-$(arch) net.zemberekserver.client.DBusClient
