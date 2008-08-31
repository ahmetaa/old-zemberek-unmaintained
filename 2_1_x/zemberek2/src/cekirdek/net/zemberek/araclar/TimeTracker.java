/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.araclar;

import java.util.HashMap;

/**
 * Hassas kronometre ihtiyaçları için tasarlanmıştır.
 * <p/>
 * Kullanmak için timeTracker.startClock(isim) dedikten sonra
 * TimeTracker.stopClock(isim)'un döndürdüğü String'i geçen süreyi göstermek 
 * için kullanabilirsiniz. Stop'tan önce ara adımları izlemek istiyorsanız 
 * TimeTracker.getElapsedTimeString(isim) veya getElapsedTimeStringAsMillis
 * metodlarini kullanabilirsiniz. Start ile başlattığınız saatleri isiniz 
 * bittigindemutlaka stop ile durdurmanız gerekiyor, çünkü ancak stop ile register
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
            System.err.println("Max Saat izleyici sayısı aşıldı. (" + MAX_TIMETRACKER_USERS + ")");
            return;
        }
        if (users.get(name) != null) {
            System.err.println(name + " isminde bir zaman izleyici zaten var.");
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
     * @return :Bir önceki tick'ten bu yana geçen süre (milisaniye cinsinden)
     */
    public static long getElapsedTime(String name) {
        TimerElement timer = users.get(name);
        if (timer == null)
            return -1;
        timer.refresh();
        return timer.getElapsedTime();
    }

    /**
     * ismi verilen saatin en son kontrolünden bu yana ne kadar zaman geçtiğini
     * milisaniye cinsinden döndürür.
     *
     * @param name :  saatin adı
     * @return :Bir önceki tick'ten bu yana geçen süre (milisaniye cinsinden)
     */
    public static long getTimeDelta(String name) {
        TimerElement timer = users.get(name);
        if (timer == null)
            return -1;
        timer.refresh();
        return timer.getDiff()/1000L;
    }

    /**
     * ismi verilen saatin en son kontrolunden (baslangic veya bir onceki tick) 
     * bu yana ne kadar zaman gectiğini ve başlangıçtan bu yana geçen süreyi 
     * virgülden sonra 3 basamaklı saniyeyi ifade eden String cinsinden döndürür.
     *
     * @param name : saatin adı
     * @return : Bir önceki tick'ten bu yana geçen süre (Binde bir hassasiyetli saniye cinsinden cinsinden)
     */
    public static String getElapsedTimeString(String name) {
        TimerElement timer = users.get(name);
        if (timer == null)
            return "Geçersiz Kronometre: " + name;
        timer.refresh();
        return "Delta: " + (double) timer.getDiff()/1000L  + " s. Elapsed: " + (double) timer.getElapsedTime()/1000L + " s.";
    }

    /**
     * @param name : saatin adı
     * @return : Bir önceki tick'ten bu yana geçen süre (milisaniye cinsinden)
     */
    public static String getElapsedTimeStringAsMillis(String name) {
        TimerElement timer =users.get(name);
        if (timer == null)
            return "Geçersiz Kronometre: " + name;
        timer.refresh();
        return "Delta: " + timer.getDiff()/1000L + "ms. Elapsed: " + timer.getElapsedTime()/1000L + "ms.";
    }

    /**
     * @param name      : saatin adı
     * @param itemCount : sure zarfında islenen nesne sayisi
     * @return : baslangictan bu yana islenen saniyedeki eleman sayisi
     */
    public static long getItemsPerSecond(String name, long itemCount) {
        TimerElement timer = users.get(name);
        if (timer == null)
            return -1;
        timer.refresh();
        long items = 0;
        if (timer.getElapsedTime() > 0)
            items = (itemCount)* 1000L / timer.getElapsedTime();
        return items;
    }

    /**
     * Saati durdurur ve başlangıçtan bu yana geçen süreyi saniye ve ms 
     * cinsinden döndürür. Ayrıca saati listeden siler. 
     *
     * @param name Saat ismi
     * @return başlangıçtan bu yana geçen süre
     */
    public static String stopClock(String name) {
        TimerElement timer = users.get(name);
        if (timer == null)
            return name + " : Geçersiz Kronometre";
        timer.refresh();
        users.remove(name);
        return "" + (float) timer.elapsedTime + "sn."
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

    private static long getMilis() {
       return System.nanoTime()/ 1000000L;
    }

    public TimerElement(String name) {
        creationTime = getMilis();
        startTime = creationTime;
        lastTime = creationTime;
        this.name = name;
    }

    public void refresh() {
        diff =getMilis() - lastTime;
        lastTime = getMilis();
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