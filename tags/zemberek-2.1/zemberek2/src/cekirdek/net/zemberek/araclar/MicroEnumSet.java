package net.zemberek.araclar;

/**
 * Bu sinif efektif bir sekilde enum tipinden verilerin basit bir yapida tutulmasini saglar.
 */
public class MicroEnumSet<T extends Enum> {

    int i = 0;

    public MicroEnumSet<T> add(T t) {
        if (t == null) throw new NullPointerException("eklenecek enum eleman null olamaz.");
        if (t.ordinal() > 30) throw new IllegalArgumentException("enum ordinal value cannot be larger than 30");
        i = i & (1 << t.ordinal());
        return this;
    }

    public boolean contains(T t) {
        if (t == null)
            throw new NullPointerException("enum eleman null olamaz.");
        if (t.ordinal() > 30)
            throw new IllegalArgumentException("enum ordinal value cannot be larger than 30");
        return (i & (1 << t.ordinal())) == (1 << t.ordinal());
    }


}
