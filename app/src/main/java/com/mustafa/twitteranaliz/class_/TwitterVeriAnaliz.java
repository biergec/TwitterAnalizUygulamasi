package com.mustafa.twitteranaliz.class_;

import android.content.Context;

import com.mustafa.twitteranaliz.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TwitterVeriAnaliz {

    public static HashMap<String,String> TakipEtmeyenleriBul(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        ArrayList<String> Takipciler=new ArrayList<>();
        ArrayList<String> TakipEdilenler_resim=new ArrayList<>();
        ArrayList<String> TakipEdilenler=new ArrayList<>();

        //takip ettiklerim takipciler arasında var mı yoksa hashmap e ekle
        try{
            TakipEdilenler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            TakipEdilenler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            for (int i=0;i<TakipEdilenler.size();i++){
                if (!Takipciler.contains(TakipEdilenler.get(i))){
                    //yoksa buraya gelir ekle
                    islenen_veri.put(TakipEdilenler.get(i),TakipEdilenler_resim.get(i));
                }
            }

            return islenen_veri;
        }catch (Exception e){
            throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }
    }

    public static HashMap<String,String> HayranlariBul(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        ArrayList<String> TakipEdilenler;
        ArrayList<String> Takipciler;
        ArrayList<String> Takipciler_resim;
        try{
            TakipEdilenler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            for (int i=0;i<Takipciler.size();i++){
                if (!TakipEdilenler.contains(Takipciler.get(i))){
                    //takip ettiklerimde yoksa hayranımdır.
                    islenen_veri.put(Takipciler.get(i),Takipciler_resim.get(i));
                }
            }
            return islenen_veri;
        }catch (Exception e){
            throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }
    }

    public static HashMap<String,String> YeniTakipEdilenleriBul(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        ArrayList<String> FirstTakipEdilenler=new ArrayList<>();
        ArrayList<String> TakipEdilenler=new ArrayList<>();
        ArrayList<String> TakipEdilenler_resim=new ArrayList<>();

        try{
            FirstTakipEdilenler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"First_TakipEdilenler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            TakipEdilenler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            TakipEdilenler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            //takip ettiklerim takipciler arasında var mı yoksa hashmap e ekle
            for (int i=0;i<FirstTakipEdilenler.size();i++){
                if (!TakipEdilenler.contains(FirstTakipEdilenler.get(i))){
                    //yoksa buraya gelir ekle
                    islenen_veri.put(TakipEdilenler.get(i),TakipEdilenler_resim.get(i));
                }
            }
            return islenen_veri;
        }catch (Exception e){
            throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }
    }

    public static HashMap<String,String> TakipBirakanlarKaybedilenTakipciler(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        ArrayList<String> First_Takipciler=new ArrayList<>();
        ArrayList<String> Takipciler=new ArrayList<>();
        ArrayList<String> Takipciler_resim=new ArrayList<>();

        try{
            First_Takipciler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"First_Takipciler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            //takip ettiklerim takipciler arasında var mı yoksa hashmap e ekle
            for (int i=0;i<First_Takipciler.size();i++){
                if (!Takipciler.contains(First_Takipciler.get(i))){
                    //yoksa buraya gelir ekle
                    islenen_veri.put(Takipciler.get(i),Takipciler_resim.get(i));
                }
            }
            return islenen_veri;
        }catch (Exception e){
            throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }
    }

    public static HashMap<String,String> TakipEtmediklerimiBul(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        // BENİ TAKİP EDEN AMA BENM ETMEDİĞİM KİŞİLER
        ArrayList<String> TakipEdilenler=new ArrayList<>();
        ArrayList<String> Takipciler=new ArrayList<>();
        ArrayList<String> Takipciler_resim=new ArrayList<>();

        try{
            TakipEdilenler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            for (int i=0;i<Takipciler.size();i++){
                if (!TakipEdilenler.contains(Takipciler.get(i))){
                    //yoksa buraya gelir ekle
                    islenen_veri.put(Takipciler.get(i),Takipciler_resim.get(i));
                }
            }
            return islenen_veri;
        }catch (Exception e){
            throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }
    }

                    // Shared preferences ile veri okunur ///

    public static HashMap<String,String> TakipEdilenKisiListesi(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        ArrayList<String> TakipEdilenler;
        ArrayList<String> TakipEdilenler_resim;

        try {
            TakipEdilenler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            TakipEdilenler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"TakipEdilenler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            for(int i=0;i<TakipEdilenler.size();i++){
                islenen_veri.put(TakipEdilenler.get(i),TakipEdilenler_resim.get(i));
            }

        } catch (Exception e) {
           throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }

        return islenen_veri;
    }

    public static HashMap<String,String> TakipcilerinListesi(Context context) throws Exception {
        HashMap<String,String> islenen_veri=new HashMap<>();
        ArrayList<String> Takipciler;
        ArrayList<String> Takipciler_resim;

        try {
            Takipciler=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler", SharedPreferencesIslemleri.IslemTuru.DegerOkumaVeri);
            Takipciler_resim=SharedPreferencesIslemleri.SharedPreferencesKayitliVeriOkuma(context,"Takipciler_resim", SharedPreferencesIslemleri.IslemTuru.DegerOkumaProfilUrl);

            for(int i=0;i<Takipciler.size();i++){
                islenen_veri.put(Takipciler.get(i),Takipciler_resim.get(i));
            }

        } catch (Exception e) {
            throw new Exception(context.getString(R.string.throw_takipEdilemlerinListesiCekilemedi));
        }

        return islenen_veri;
    }

}
