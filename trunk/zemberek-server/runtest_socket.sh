#!/bin/sh
source runbase.sh
java -cp zemberek-server-${surum}.jar \
	-Djava.library.path=lib/native-$(uname -m) net.zemberekserver.client.SocketClient
