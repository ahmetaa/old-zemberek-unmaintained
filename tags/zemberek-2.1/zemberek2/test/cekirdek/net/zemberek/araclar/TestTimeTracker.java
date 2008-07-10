/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

/*
 * Created on 13.Mar.2004
 */
package net.zemberek.araclar;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author MDA & GBA
 */
public class TestTimeTracker {

    @Test
    public void testStartClock() {
        TimeTracker.startClock("x");
        sleepMe(300);
        long time = TimeTracker.getElapsedTime("x");
        assertTrue(time >= 300 && time < 400);
        System.out.println(TimeTracker.stopClock("x"));
    }

    //@Test
    @Ignore
    public void testDelta() {
        TimeTracker.startClock("x");
        sleepMe(300);
        long time = TimeTracker.getElapsedTime("x");
        assertTrue(time >= 300 && time < 400);
        sleepMe(300);
        long delta = TimeTracker.getTimeDelta("x");
        time = TimeTracker.getElapsedTime("x");
        assertTrue(time >= 600 && time < 700);
        assertTrue(delta >= 300 && delta < 400);
        System.out.println(TimeTracker.stopClock("x"));
    }

    private void sleepMe(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
