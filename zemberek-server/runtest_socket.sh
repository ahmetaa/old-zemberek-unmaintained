#!/bin/sh
java -cp .:lib/zemberek-cekirdek-2.1_rc1.jar:lib/zemberek-tr-2.1_rc1.jar:lib/mina-core-1.1.0.jar:dist/zemberek-server-0.7.1.jar \
	-Djava.library.path=lib/native-$(arch) net.zemberekserver.client.SocketClient
