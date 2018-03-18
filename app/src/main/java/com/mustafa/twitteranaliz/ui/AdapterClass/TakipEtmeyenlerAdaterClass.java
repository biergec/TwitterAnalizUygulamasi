package com.mustafa.twitteranaliz.ui.AdapterClass;


import android.content.Context;

public class TakipEtmeyenlerAdaterClass {
    private String resimURL,kullaniciAd,butonYazi;
    private Context context;

    public TakipEtmeyenlerAdaterClass(String resimURL, String kullaniciAd, String butonYazi, Context context) {
        this.resimURL = resimURL;
        this.kullaniciAd = kullaniciAd;
        this.butonYazi = butonYazi;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public TakipEtmeyenlerAdaterClass() {
    }

    public String getResimURL() {
        return resimURL;
    }

    public void setResimURL(String resimURL) {
        this.resimURL = resimURL;
    }

    public String getKullaniciAd() {
        return kullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        this.kullaniciAd = kullaniciAd;
    }

    public String getButonYazi() {
        return butonYazi;
    }

    public void setButonYazi(String butonYazi) {
        this.butonYazi = butonYazi;
    }
}
