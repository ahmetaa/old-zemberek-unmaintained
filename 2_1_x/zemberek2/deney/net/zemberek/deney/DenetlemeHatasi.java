package net.zemberek.deney;

public class DenetlemeHatasi {
	private String hatalikelime;
	private int hataPozisyonu;
	private String[] oneriler;
	public DenetlemeHatasi(int hataPozisyonu, String hatalikelime, String[] oneriler) {
		this.hatalikelime = hatalikelime;
		this.hataPozisyonu = hataPozisyonu;
		this.oneriler = oneriler;
	}
	public String getHatalikelime() {
		return hatalikelime;
	}
	public int getHataPozisyonu() {
		return hataPozisyonu;
	}
	public String[] getOneriler() {
		return oneriler;
	}
}
