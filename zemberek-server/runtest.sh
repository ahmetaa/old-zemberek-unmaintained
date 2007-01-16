#!/bin/sh
java -cp .:lib/dbus.jar:zemberek_server-0.6.jar -Djava.library.path=lib net.zemberekserver.client.SocketClient
java -cp .:lib/dbus.jar:lib/unix.jar:lib/hexdump.jar:zemberek_server_dbus-0.6.jar:zemberek_server-0.6.jar -Djava.library.path=lib net.zemberekserver.client.DBusClient
