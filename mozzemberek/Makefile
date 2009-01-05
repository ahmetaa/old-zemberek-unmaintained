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

include mozzemberek.config
.PHONY: clean dist-gzip all xpi extension-files install-unpacked

all: xpi

xpi:
	$(MAKE) -e -C src xpi

extension-files:
	$(MAKE) -e -C src extension-files

install-unpacked:
	$(MAKE) -e -C src install-unpacked

# Rules for creating the source distribution
SRCDIST=README INSTALL ChangeLog COPYING TODO AUTHORS \
        Makefile mozzemberek.config $(shell find src -type f)

dist: mozzemberek-$(MOZZEMBEREK_VERSION).tar.gz

mozzemberek-$(MOZZEMBEREK_VERSION).tar.gz: $(patsubst %,mozzemberek-$(MOZZEMBEREK_VERSION)/%, \
                                       $(sort $(SRCDIST)))
	tar c --group 0 --owner 0 mozzemberek-$(MOZZEMBEREK_VERSION) | gzip -9 > $@

$(patsubst %,mozzemberek-$(MOZZEMBEREK_VERSION)/%, $(sort $(SRCDIST))): \
           mozzemberek-$(MOZZEMBEREK_VERSION)/%: %
	install --mode=644 -D $^ $@


# Clean rules
clean:
	rm -rf mozzemberek-$(MOZZEMBEREK_VERSION)
	rm -f mozzemberek-$(MOZZEMBEREK_VERSION).tar.gz
	$(MAKE) -C src clean
