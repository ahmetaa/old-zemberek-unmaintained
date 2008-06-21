package net.zemberek.araclar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: aakin
 * Date: Jun 17, 2008,10:39:06 AM
 */
public class MicroSet<T> {

    Object[] container;


    public MicroSet(int length) {
        container = new Object[length];
    }

    public int size() {
        return container.length;
    }

    public MicroSet<T> ekle(T t) {
        if (t == null) throw new NullPointerException("eklenecek eleman null olamaz.");
        if (container.length == 0) {
            container = new Object[1];
            container[0] = t;
        } else {
            if (contains(t))
                return this;
            Object[] yeni = new Object[container.length + 1];
            //burada elle dizi kopyalama yapiyoruz, cunku genellikle ozel durum sayisi 1
            System.arraycopy(container, 0, yeni, 0, container.length);
            yeni[container.length] = t;
            this.container = yeni;
        }
        return this;
    }


    public boolean contains(T t) {
        for (Object o : container) {
            if (o == t || t.equals(o))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String[] ss = {"Hello", "Welcome", "seses"};
        Set<String> s = new HashSet<String>(Arrays.asList(ss));

        boolean c = false;
        long st = System.currentTimeMillis();
        for (int ikj = 0; ikj < 5; ikj++) {
            st = System.currentTimeMillis();
            for (int i = 0; i < 10000000; i++) {
                c = s.contains("Welcome") || s.contains("ffffs");
            }
            System.out.println("c = " + c);
            System.out.println("time:" + (System.currentTimeMillis() - st));
        }

        System.out.println("sdsdsdsdsdsdsdsdsds");
        c = false;
        MicroSet<String> set = new MicroSet<String>(0);
        for (String s1 : s) {
            set.ekle(s1);
        }

        for (int ikj = 0; ikj < 5; ikj++) {
            st = System.currentTimeMillis();
            for (int i = 0; i < 10000000; i++) {
                c = set.contains("Welcome") || set.contains("ffffs");
            }
            System.out.println("c = " + c);
            System.out.println("time:" + (System.currentTimeMillis() - st));
        }


    }

}
