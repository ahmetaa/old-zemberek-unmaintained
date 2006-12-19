#!/bin/sh
#FIXME:	dbus izinlerinden dolayı zemberek-server.conf dosyası
#		sisteme yüklenmeden dbus sunucusu çalışmıyor
java -cp lib/netty2.jar:lib/zemberek2-tr.jar:lib/dbus.jar -Djava.library.path=lib -Xverify:none -Xms12m -Xmx14m -jar zemberek_server-0.6.jar