#!/bin/sh
source runbase.sh
java -cp zemberek-server-${surum}.jar \
	-Djava.library.path=lib/$(get_jni_dir) net.zemberekserver.client.SocketClient
