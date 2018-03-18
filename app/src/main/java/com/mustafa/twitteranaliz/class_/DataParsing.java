package com.mustafa.twitteranaliz.class_;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataParsing {

    public static ArrayList<String> KisiListesiTespit(HashMap<String,HashMap<String,String>> parse_edilecek_veri){
        ArrayList<String> kisiListesi = new ArrayList<String>();
        try{
            for (Map.Entry<String, HashMap<String, String>> letterEntry : parse_edilecek_veri.entrySet()) {
                String kullaniciAdi = letterEntry.getKey();
                kisiListesi.add(kullaniciAdi);
            }
        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        return  kisiListesi;
    }

    public static ArrayList<String> KullanıcıProfilResimleriTespit(HashMap<String,HashMap<String,String>> parse_edilecek_veri){

        ArrayList<String> hashmap_okunan_degerler=new ArrayList<>();
        try{
            for (Map.Entry<String, HashMap<String, String>> letterEntry : parse_edilecek_veri.entrySet()) {
                for (Map.Entry<String, String> nameEntry : letterEntry.getValue().entrySet()) {
                    if (nameEntry.getKey().equals("ProfilResimUrl")){
                        hashmap_okunan_degerler.add(nameEntry.getValue());
                    }
                }
            }
        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        return hashmap_okunan_degerler;
    }
}
