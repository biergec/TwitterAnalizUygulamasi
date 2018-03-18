package com.mustafa.twitteranaliz.Thread;

import android.content.Context;
import android.util.Log;

import com.mustafa.twitteranaliz.GlobalBus.KullaniciBilgileriCekildi;
import com.mustafa.twitteranaliz.class_.DataParsing;
import com.mustafa.twitteranaliz.class_.SharedPreferencesIslemleri;
import com.mustafa.twitteranaliz.twitterRest.Followers;
import com.mustafa.twitteranaliz.twitterRest.MyTwitterApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mustafa.twitteranaliz.twitterRest.Followers.UserFollowersList;

public class ThreadUserFollowers implements Runnable {

    private String userName;
    private String cursor;
    private TwitterSession session;
    private Context context;

    public ThreadUserFollowers(String userName, String cursor, TwitterSession session, Context context) {
        this.userName = userName;
        this.cursor = cursor;
        this.session = session;
        this.context = context;
    }

    @Override
    public void run() {
        new MyTwitterApiClient(session).getFollowers().show(userName,cursor,"true","false","198")
               .enqueue(new Callback<Followers>() {
                   @Override
                   public void success(Result<Followers> result) {
                       Followers followers= new Followers(result.data.getUsers());
                       if (cursor.equals("-1")){
                           UserFollowersList=new ArrayList();
                       }
                       UserFollowersList.add(followers);
                       if (!(result.data.getNext_cursor_str().equals("0"))){
                           String cursor_next=result.data.getNext_cursor_str();
                           ThreadUserFollowers threadUserFollowersx=new ThreadUserFollowers(userName,cursor_next,session,context);
                           threadUserFollowersx.run();
                       }else {
                           try {
                               UserFollowersDuzenleme(UserFollowersList);
                           } catch (Exception e) {
                               Log.d("Error",e.getMessage());
                           }
                       }
                   }

                   @Override
                   public void failure(TwitterException exception) {
                       Log.d("Error",exception.getMessage());
                   }

               });
    }

    private void UserFollowersDuzenleme(ArrayList<Followers> userFollowersList) throws Exception {
        HashMap<String,String> h_userInfo_Followers;
        HashMap<String,HashMap<String,String>> h_userName_Followers = new HashMap<>();

        for (int i=0;i<userFollowersList.size();i++){
            for (int j=0;j<userFollowersList.get(i).getUsers().size();j++){

                h_userInfo_Followers = new HashMap<>();
                //ScreenNane username demek
                h_userInfo_Followers.put("KullaniciAdi",userFollowersList.get(i).getUsers().get(j).screenName);
                h_userInfo_Followers.put("GercekAdi",userFollowersList.get(i).getUsers().get(j).name);
                h_userInfo_Followers.put("ProfilResimUrl",userFollowersList.get(i).getUsers().get(j).profileImageUrlHttps);
                h_userInfo_Followers.put("Konum",userFollowersList.get(i).getUsers().get(j).location);

                //Daha önce eklenmediyse ekle
                if (!h_userName_Followers.containsKey(h_userInfo_Followers.get("KullaniciAdi"))){
                    //Daha sonra kullanıcı adı ile yeni bir hashmap'e ekliyorum
                    h_userName_Followers.put(h_userInfo_Followers.get("KullaniciAdi"),h_userInfo_Followers);
                }
            }
        }

        try{
            ArrayList<String> kisiListesi= DataParsing.KisiListesiTespit(h_userName_Followers);
            ArrayList<String>kisiListesiProfilUrl=DataParsing.KullanıcıProfilResimleriTespit(h_userName_Followers);

            SharedPreferencesIslemleri.SharedPreferencesStringDizisiniKayitEt(context,kisiListesi, SharedPreferencesIslemleri.IslemTuru.Takipci,kisiListesiProfilUrl);
            EventBus.getDefault().post(new KullaniciBilgileriCekildi());
        }catch (Exception e){
            Log.d("Error",e.getMessage());
            throw new Exception("Takipci Listesi Kayit Edilemedi");
        }
    }
}
