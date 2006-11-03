/*
 * Created on 10.Mar.2004
 */
package net.zemberek.araclar;

import java.util.HashMap;

/**
 * Hassas kronometre ihtiyaçları için tasarlanmıştır.
 * <p/>
 * Kullanmak için timeTracker.startClock(isim) dedikten sonra
 * TimeTracker.stopClock(isim)'un döndürdüğü String'i geçen süreyi göstermek 
 * için kullanabilirsiniz. Stop'tan �nce ara adımları izlemek istiyorsanız 
 * TimeTracker.getElapsedTimeString(isim) veya getElapsedTimeStringAsMillis
 * metodlarini kullanabilirsiniz. Start ile başlattığınız saatleri isiniz 
 * bittigindemutlaka stop ile durdurmanız gerekiyor, ��nk� ancak stop ile register
 * olmuş bir saat nesnesini unregistr edebilirsiniz.
 * <p/>
 * Olusan saatler globaldir, yani programin icinde istediginiz her yerde
 * kullanabilirsiniz.
 *
 * @author M.D.A
 */
public class TimeTracker {
    public static int MAX_TIMETRACKER_USERS = 500;
    private static HashMap<String, TimerElement> users = new HashMap<String, TimerElement>();

    /**
     * Yeni bir saat oluşturur ve listeye register eder.
     * @param name : saat adı
     */
    public static void startClock(String name) {
        if (users.size() > MAX_TIMETRACKER_USERS) {
            System.out.println("Max Saat izleyici say�s� a��ld�. (" + MAX_TIMETRACKER_USERS + ")");
            return;
        }
        if (users.get(name) != null) {
            System.out.println(name + " isminde bir zaman izleyici zaten var.");
            return;
        }
        TimerElement timer = new TimerElement(name);
        users.put(name, timer);
    }

    /**
     * ismi verilen saat için başlangıçtan bu yana bu yana ne kadar zaman 
     * geçtiğini milisaniye cinsinden döndürür.
     *
     * @param name : saatin adı
     * @return :Bir �nceki tick'ten bu yana geçen süre (milisaniye cinsinden)
     */
    public static long getElapsedTime(String name) {
        TimerElement timer = (TimerElement) users.get(name);
        if (timer == null)
            return -1;
        timer.refresh();
        return timer.getElapsedTime();
    }

    /**
     * ismi verilen saatin en son kontrolünden bu yana ne kadar zaman ge�ti�ini
     * milisaniye cinsinden döndürür.
     *
     * @param name :  saatin adı
     * @return :Bir �nceki tick'ten bu yana geçen süre (milisaniye cinsinden)
     */
    public static long getTimeDelta(String name) {
        TimerElement timer = (TimerElement) users.get(name);
        if (timer == null)
            return -1;
        timer.refresh();
        return timer.getDiff();
    }

    /**
     * ismi verilen saatin en son kontrolunden (baslangic veya bir onceki tick) 
     * bu yana ne kadar zaman gectiğini ve başlangıçtan bu yana geçen süreyi 
     * virgülden sonra 3 basamaklı saniyeyi ifade eden String cinsinden döndürür.
     *
     * @param name : saatin adı
     * @return : Bir �nceki tick'ten bu yana ge�en s�re (Binde bir hassasiyetli saniye cinsinden cinsinden)
     */
    public static String getElapsedTimeString(String name) {
        TimerElement timer = (TimerElement) users.get(name);
        if (timer == null)
            return "Geçersiz Kronometre: " + name;
        timer.refresh();
        return "Delta: " + (float) timer.getDiff() / 1000 + " s. Elapsed: " + (float) timer.getElapsedTime() / 1000 + " s.";
    }

    /**
     * @param name : saatin adı
     * @return : Bir �nceki tick'ten bu yana geçen süre (milisaniye cinsinden)
     */
    public static String getElapsedTimeStringAsMillis(String name) {
        TimerElement timer = (TimerElement) users.get(name);
        if (timer == null)
            return "Ge�ersiz Kronometre: " + name;
        timer.refresh();
        return "Delta: " + timer.getDiff() + "ms. Elapsed: " + timer.getElapsedTime() + "ms.";
    }

    /**
     * @param name      : saatin adı
     * @param itemCount : sure zarfında islenen nesne sayisi
     * @return : baslangictan bu yana islenen saniyedeki eleman sayisi
     */
    public static long getItemsPerSecond(String name, long itemCount) {
        TimerElement timer = (TimerElement) users.get(name);
        if (timer == null)
            return -1;
        timer.refresh();
        long items = 0;
        if (timer.getElapsedTime() > 0)
            items = (1000 * itemCount) / timer.getElapsedTime();
        return items;
    }

    /**
     * Saati durdurur ve başlangıçtan bu yana geçen süreyi saniye ve ms 
     * cinsinden döndürür. Ayrıca saati listeden siler ��kar�r.
     *
     * @param name Saat ismi
     * @return başlangıçtan bu yana geçen süre
     */
    public static String stopClock(String name) {
        TimerElement timer = (TimerElement) users.get(name);
        if (timer == null)
            return name + " : Geçersiz Kronometre";
        timer.refresh();
        users.remove(name);
        return "" + (float) timer.elapsedTime / 1000 + "sn." 
               + "(" + timer.elapsedTime + " ms.)";
    }
}

/**
 * isimlendirilmiş Zaman bilgisi taşıyıcı.
 *
 * @author MDA
 */
class TimerElement {
    String name;
    long startTime = 0;
    long stopTime = 0;
    long lastTime = 0;
    long creationTime = 0;
    long elapsedTime = 0;
    long diff = 0;

    public TimerElement(String name) {
        creationTime = System.currentTimeMillis();
        startTime = creationTime;
        lastTime = creationTime;
        this.name = name;
    }

    public void refresh() {
        diff = System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        elapsedTime = lastTime - startTime;
    }

    public long getDiff() {
        return diff;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public String getName() {
        return name;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStopTime() {
        return stopTime;
    }
}