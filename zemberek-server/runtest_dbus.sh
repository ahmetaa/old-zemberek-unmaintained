#!/bin/sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp .:lib/dbus.jar:lib/unix.jar:dist/zemberek_server-0.7.1.jar \
	-Djava.library.path=lib net.zemberekserver.client.DBusClient
