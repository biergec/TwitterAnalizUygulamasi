package com.mustafa.twitteranaliz.class_;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mustafa.twitteranaliz.R;

import java.util.ArrayList;

public class SharedPreferencesIslemleri {

    public enum IslemTuru{
        Takipci,
        TakipEdilen,
        DegerOkumaProfilUrl,
        DegerOkumaVeri
    }

    // varsa true
    public static boolean SharedPreferencesKayitKontrol(Context context, String kayitKontrolDegiskeni) throws Exception {
        try{
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
            String preferencesOkunanDeger=preferences.getString(kayitKontrolDegiskeni,("-1"));

            if (!preferencesOkunanDeger.equals("-1")) {
                return true;
            }
            return false;
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            throw  new Exception(context.getString(R.string.throw_KayitKontrolBasarisiz));
        }
    }

    public static void SharedPreferencesKayitSilme(Context context, String silinecekDeger) throws Exception {
        try{
            SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.remove(silinecekDeger);
            editor.apply();
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            throw  new Exception(context.getString(R.string.throw_KayitSilmeBasarisiz));
        }
    }

    public static void SharedPreferencesStringDizisiniKayitEt(Context context, ArrayList<String> gelenList, IslemTuru tur, ArrayList<String> resimUrller) throws Exception {
        try{
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor=preferences.edit();

            String kayitEdilecekVeri="";
            for (String x:gelenList){
                kayitEdilecekVeri=kayitEdilecekVeri+"-"+x;
            }
            Log.d("kayitEdilecekVeri",kayitEdilecekVeri);


            String kayitEdilecekVeriProfilResimleri="";
            for (String x:resimUrller){
                kayitEdilecekVeriProfilResimleri=kayitEdilecekVeriProfilResimleri+"-!-"+x;
            }
            Log.d("kayitEdilecekVeriResim",kayitEdilecekVeriProfilResimleri);

            //
            if (SharedPreferencesIslemleri.SharedPreferencesKayitKontrol(context,"Takipciler") && SharedPreferencesIslemleri.SharedPreferencesKayitKontrol(context,"TakipEdilenler")  ){

                //varsa sil ve yeniden yükle
                if (tur==IslemTuru.Takipci){
                    SharedPreferencesIslemleri.SharedPreferencesKayitSilme(context,"Takipciler");
                    SharedPreferencesIslemleri.SharedPreferencesKayitSilme(context,"Takipciler_resim");
                    editor.putString("Takipciler",kayitEdilecekVeri);
                    editor.putString("Takipciler_resim",kayitEdilecekVeriProfilResimleri);
                    editor.apply();
                }

                if (tur==IslemTuru.TakipEdilen){
                    SharedPreferencesIslemleri.SharedPreferencesKayitSilme(context,"TakipEdilenler");
                    SharedPreferencesIslemleri.SharedPreferencesKayitSilme(context,"TakipEdilenler_resim");
                    editor.putString("TakipEdilenler",kayitEdilecekVeri);
                    editor.putString("TakipEdilenler_resim",kayitEdilecekVeriProfilResimleri);
                    editor.apply();
                }

            }else {
                // yoksa ilk kayit yap, ayrıca farklı bir isimde de karşılaştırma için ekle
                if (tur==IslemTuru.Takipci){
                    editor.putString("First_Takipciler",kayitEdilecekVeri);
                    editor.putString("First_Takipciler_resim",kayitEdilecekVeriProfilResimleri);
                    editor.putString("Takipciler",kayitEdilecekVeri);
                    editor.putString("Takipciler_resim",kayitEdilecekVeriProfilResimleri);
                    editor.apply();
                }

                if (tur==IslemTuru.TakipEdilen){
                    editor.putString("First_TakipEdilenler",kayitEdilecekVeri);
                    editor.putString("First_TakipEdilenler_resim",kayitEdilecekVeriProfilResimleri);
                    editor.putString("TakipEdilenler",kayitEdilecekVeri);
                    editor.putString("TakipEdilenler_resim",kayitEdilecekVeriProfilResimleri);
                    editor.apply();
                }
            }
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            throw  new Exception(context.getString(R.string.throw_StringDizisiKayit));
        }
    }

    public static ArrayList<String > SharedPreferencesKayitliVeriOkuma(Context context, String okunacakDegerKey, IslemTuru islemTuru) throws Exception {
        try{
            ArrayList<String> list=new ArrayList<>();
            String[] okunanDegerlerDizi = new String[2];

            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
            String preferencesOkunanDeger=preferences.getString(okunacakDegerKey,"");

            //Kayit varsa oku
            if (!preferencesOkunanDeger.equals("")) {
                
                if (islemTuru==IslemTuru.DegerOkumaVeri){
                    okunanDegerlerDizi=preferencesOkunanDeger.split("-");
                }

                if (islemTuru==IslemTuru.DegerOkumaProfilUrl){
                    okunanDegerlerDizi=preferencesOkunanDeger.split("-!-");
                }

                for (String x:okunanDegerlerDizi){
                    list.add(x);
                }
                Log.d("Error","");
                return list;
            }
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            throw new Exception(context.getString(R.string.throw_KayitliVerilerOkunamadi));
        }
        return null;
    }

}
