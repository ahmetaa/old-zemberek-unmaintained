#!/bin/sh
java -DUSE_DBUS_SYSTEM_CONNECTION=no \
	-cp zemberek-server-0.7.1_rc1.jar \
	-Djava.library.path=lib/native-$(arch) net.zemberekserver.client.DBusClient
