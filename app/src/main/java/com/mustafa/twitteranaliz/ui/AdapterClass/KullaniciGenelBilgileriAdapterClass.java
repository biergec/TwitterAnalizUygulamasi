package com.mustafa.twitteranaliz.ui.AdapterClass;

public class KullaniciGenelBilgileriAdapterClass {
    private String sayisalBilgi,bilgilendirmeMetni;
    private int ustResim;
    public KullaniciGenelBilgileriAdapterClass(int ustResim, String sayisalBilgi, String bilgilendirmeMetni) {
        this.ustResim = ustResim;
        this.sayisalBilgi = sayisalBilgi;
        this.bilgilendirmeMetni = bilgilendirmeMetni;
    }

    public int getUstResim() {
        return ustResim;
    }

    public void setUstResim(int ustResim) {
        this.ustResim = ustResim;
    }

    public String getSayisalBilgi() {
        return sayisalBilgi;
    }

    public void setSayisalBilgi(String sayisalBilgi) {
        this.sayisalBilgi = sayisalBilgi;
    }

    public String getBilgilendirmeMetni() {
        return bilgilendirmeMetni;
    }

    public void setBilgilendirmeMetni(String bilgilendirmeMetni) {
        this.bilgilendirmeMetni = bilgilendirmeMetni;
    }
}
