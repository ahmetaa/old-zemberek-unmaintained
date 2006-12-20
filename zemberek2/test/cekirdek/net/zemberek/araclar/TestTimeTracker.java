/*
 *  ***** BEGIN LICENSE BLOCK *****
 *
 *  Version: MPL 1.1
 *
 *  The contents of this file are subject to the Mozilla Public License Version
 *  1.1 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  http://www.mozilla.org/MPL/
 *
 *  Software distributed under the License is distributed on an "AS IS" basis,
 *  WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 *  for the specific language governing rights and limitations under the
 *  License.
 *
 *  The Original Code is "Zemberek Dogal Dil Isleme Kutuphanesi"
 *
 *  The Initial Developer of the Original Code is
 *  Ahmet A. Akin, Mehmet D. Akin.
 *  Portions created by the Initial Developer are Copyright (C) 2006
 *  the Initial Developer. All Rights Reserved.
 *
 *  Contributor(s):
 *
 *  ***** END LICENSE BLOCK *****
 */

/*
 * Created on 13.Mar.2004
 */
package net.zemberek.araclar;

import static org.junit.Assert.assertTrue;
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

    @Test
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
