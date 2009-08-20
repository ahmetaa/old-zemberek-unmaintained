/*
 * Lisans bilgisi icin lutfen proje ana dizinindeki zemberek2-lisans.txt dosyasini okuyunuz.
 */

package net.zemberek.islemler.cozumleme;

/**
 * Bu enumerasyon sinifi cozumleme islemi stratejisini belirler.
 */
public enum CozumlemeSeviyesi {
    /** denetleme isleminde kullanilir. sadece tek kok-ek cozumunun bulunacagini isaret eder. */
    TEK_KOK,
    /** kok bulma isleminde kullanilir. tum olasi kokler icin cozumleri bul. */
    TUM_KOKLER,
    /** butun olasi kokler ve her kok icin tum olasi ekler icin cozumleme yap. */
    TUM_KOK_VE_EKLER
}
