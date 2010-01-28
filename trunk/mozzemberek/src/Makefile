# Copyright (C) 2008 Harri Pitkänen <hatapitk@iki.fi>
# Copyright (C) 2008 Rail Aliev <rail@i-rs.ru>
#
# This program is free software; you can redistribute it and/or
# modify it under the terms of the GNU General Public License
# as published by the Free Software Foundation; either version 2
# of the License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

XULRUNNER_SDK=$(shell pkg-config --variable=sdkdir libxul)
XULRUNNER_INCDIR=$(shell pkg-config --variable=includedir libxul)
XULRUNNER_LIBS=$(shell pkg-config xulrunner-xpcom --libs)
NSPR_INCLUDES=$(shell pkg-config --cflags-only-I nspr)
MOZILLA_FIVE_HOME?=/usr/lib/firefox
CC_LINK=$(CXX) -shared -Wl,--no-undefined -fPIC
include ../mozzemberek.config

CPU_ARCH = $(shell uname -m | sed -e s/i.86/x86/ )
TARGET_COMPILER_ABI = gcc3
OS_TARGET = Linux
#OS_TARGET = $(shell uname)
PLATFORM = $(OS_TARGET)_$(CPU_ARCH)-$(TARGET_COMPILER_ABI)

XULRUNNER_INCLUDES=-I$(XULRUNNER_SDK)/sdk/include -I$(XULRUNNER_SDK)/include \
-I$(XULRUNNER_INCDIR)/xpcom \
-I$(XULRUNNER_INCDIR)/spellchecker \
-I$(XULRUNNER_INCDIR)/uconv \
-I$(XULRUNNER_INCDIR)/unicharutil \
-I$(XULRUNNER_INCDIR)/xulapp \
-I$(XULRUNNER_INCDIR)/unstable

HEADERS=mozZemberek.h  zemberek.h  zsconn.h  zstring.h
OBJECTS=mozZemberek mozZemberekFactory zemberek zsconn zstring
COMPONENT=libmozzemberek.so
EXTENSION=mozzemberek-$(MOZZEMBEREK_VERSION)-$(PLATFORM).xpi
UUID=mozzemberek@mozilla.org.tr

CFLAGS?=-O2
CC_FLAGS=$(CFLAGS) -Wall -Wno-non-virtual-dtor -fno-rtti -fno-exceptions -fshort-wchar -fPIC -c
CC_INCLUDES=$(NSPR_INCLUDES) $(XULRUNNER_INCLUDES) \
-include mozilla-config.h -include xpcom-config.h
CC_LIBS=$(XULRUNNER_LIBS)
STRIP?=strip

.PHONY: all clean xpi extension-files install-unpacked chrome/mozzemberek.jar

all: xpi

xpi: $(EXTENSION)

extension-files: platform/$(PLATFORM)/components/$(COMPONENT) install.rdf

$(EXTENSION): platform/$(PLATFORM)/components/$(COMPONENT) install.rdf chrome.manifest chrome/mozzemberek.jar defaults/preferences/prefs.js
	zip -r $@ $^

chrome/mozzemberek.jar:
	cd chrome && zip mozzemberek.jar -r content locale

platform/$(PLATFORM)/components/$(COMPONENT): $(patsubst %,%.o,$(OBJECTS))
	-mkdir -p platform/$(PLATFORM)/components
	$(CC_LINK) $(LDFLAGS) -o $@ $^ $(CC_LIBS)
	-$(STRIP) $@

%.o: %.cpp
	$(CXX) $(CC_FLAGS) $(CC_INCLUDES) -o $@ $<

INSTALL_RDF_SEDSCRIPT="s/@FF_MIN@/$(MOZZEMBEREK_FF_MIN)/g; \
	s/@FF_MAX@/$(MOZZEMBEREK_FF_MAX)/g; \
	s/@TB_MIN@/$(MOZZEMBEREK_TB_MIN)/g; \
	s/@TB_MAX@/$(MOZZEMBEREK_TB_MAX)/g; \
	s/@SM_MIN@/$(MOZZEMBEREK_SM_MIN)/g; \
	s/@SM_MAX@/$(MOZZEMBEREK_SM_MAX)/g; \
	s/@PRISM_MIN@/$(MOZZEMBEREK_PRISM_MIN)/g; \
	s/@PRISM_MAX@/$(MOZZEMBEREK_PRISM_MAX)/g; \
	s/@VERSION@/$(MOZZEMBEREK_VERSION)/g; \
	/@TARGET_PLATFORM@/d"
install.rdf: install.rdf.in
	sed -e $(INSTALL_RDF_SEDSCRIPT) < $^ > $@

clean:
	$(RM) $(patsubst %,%.o,$(OBJECTS)) $(EXTENSION) install.rdf chrome/mozzemberek.jar
	$(RM) -r platform

install-unpacked: extension-files
	install -m 755 -d '$(DESTDIR)/$(MOZILLA_FIVE_HOME)/extensions/$(UUID)' '$(DESTDIR)/$(MOZILLA_FIVE_HOME)/extensions/$(UUID)/platform/$(PLATFORM)/components'
	install -m 644 'platform/$(PLATFORM)/components/$(COMPONENT)' '$(DESTDIR)/$(MOZILLA_FIVE_HOME)/extensions/$(UUID)/platform/$(PLATFORM)/components'
	install -m 644 install.rdf '$(DESTDIR)/$(MOZILLA_FIVE_HOME)/extensions/$(UUID)'
	install -m 644 chrome.manifest '$(DESTDIR)/$(MOZILLA_FIVE_HOME)/extensions/$(UUID)'
