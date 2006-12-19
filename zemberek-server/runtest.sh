#!/bin/sh
java -cp .:lib/dbus.jar:zemberek_server-0.6.jar -Djava.library.path=lib net.zemberekserver.client.TestClient
java -cp .:lib/dbus.jar:zemberek_server-0.6.jar -Djava.library.path=lib net.zemberekserver.client.TestDBus