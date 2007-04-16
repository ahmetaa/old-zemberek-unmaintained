#!/bin/sh
java -cp .:zemberek_server-0.7.jar:lib/zemberek-cekirdek-2.0.jar:lib/zemberek-tr-2.0.jar -Djava.library.path=lib net.zemberekserver.client.SocketClient