#!/bin/sh
java -cp zemberek-server-0.7.1_rc1.jar \
	-Djava.library.path=lib/native-$(arch) net.zemberekserver.client.SocketClient
