package net.zemberekserver.server;

public abstract class ZemberekMesaji implements Cloneable {

    transient protected int mesajId;
    transient protected int tip;
    private String mesaj = null;

    protected ZemberekMesaji() {
    }
    
	protected ZemberekMesaji(int tip, String mesaj) {
		this.tip = tip;
        this.mesaj = mesaj;
	}

    public int getMesajId() {
        return mesajId;
    }
    public void setMesajId(int mesajId) {
        this.mesajId = mesajId;
    }
    public int getTip() {
        return tip;
    }
    
    public String toString() {
        return mesaj;
    }
    
    public Object clone() {
        try {
            return  super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMesaj() {
        return mesaj;
    }
    
}