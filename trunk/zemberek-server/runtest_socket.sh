#!/bin/sh
source runbase.sh
java -cp zemberek-server-${surum}.jar \
	net.zemberekserver.client.socket.SocketClient
