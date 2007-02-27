package net.zemberek.deney;

import junit.framework.TestCase;

public class TestZemberekFactory extends TestCase {

	public void testGetZemberek() {
		//Aynı referansı döndürmesi gerekiyor ondan == ile kontrol ettim
		assertTrue(ZemberekFactory.getZemberek("tr")==ZemberekFactory.getZemberek("tr"));
		assertTrue(ZemberekFactory.getZemberek("tm")==ZemberekFactory.getZemberek("tm"));
	}

}
