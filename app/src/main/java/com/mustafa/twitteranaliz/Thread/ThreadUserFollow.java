package com.mustafa.twitteranaliz.Thread;

import android.content.Context;
import android.util.Log;

import com.mustafa.twitteranaliz.class_.DataParsing;
import com.mustafa.twitteranaliz.class_.SharedPreferencesIslemleri;
import com.mustafa.twitteranaliz.twitterRest.Follow;
import com.mustafa.twitteranaliz.twitterRest.MyTwitterApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.HashMap;

import static com.mustafa.twitteranaliz.twitterRest.Follow.UserFollowList;

public class ThreadUserFollow implements Runnable {

    private String userName,cursor;
    private TwitterSession session;
    private Context context;

    public ThreadUserFollow(String userName, String cursor, TwitterSession session, Context context) {
        this.userName = userName;
        this.cursor = cursor;
        this.session = session;
        this.context = context;
    }

    @Override
    public void run() {
        new MyTwitterApiClient(session).getFollow().show(userName,cursor,"true","false","198")
                .enqueue(new Callback<Follow>() {
                    @Override
                    public void success(Result<Follow> result) {
                        Follow follow=new Follow(result.data.getUsers());
                        if (cursor.equals("-1")){
                            UserFollowList=new ArrayList<>();
                        }
                        UserFollowList.add(follow);
                        if (!(result.data.getNext_cursor_str().equals("0"))){
                            String cursor_next=result.data.getNext_cursor_str();
                            ThreadUserFollow threadUserFollow2=new ThreadUserFollow(userName,cursor_next,session,context);
                            threadUserFollow2.run();
                        }else {
                            UserFollowDuzenleme(UserFollowList);
                        }
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("Error",exception.getMessage());
                    }

                });
    }

    private void UserFollowDuzenleme(ArrayList<Follow> UserFollowList) {
        HashMap<String,String> h_userInfo_Follow;
        HashMap<String,HashMap<String,String>> h_userName_Follow = new HashMap<>();

        for (int i=0;i<UserFollowList.size();i++){
            for (int j=0;j<UserFollowList.get(i).getUsers().size();j++){

                h_userInfo_Follow = new HashMap<>();
                //ScreenNane username demek
                h_userInfo_Follow.put("KullaniciAdi",UserFollowList.get(i).getUsers().get(j).screenName);
                h_userInfo_Follow.put("GercekAdi",UserFollowList.get(i).getUsers().get(j).name);
                h_userInfo_Follow.put("ProfilResimUrl",UserFollowList.get(i).getUsers().get(j).profileImageUrlHttps);
                h_userInfo_Follow.put("Konum",UserFollowList.get(i).getUsers().get(j).location);

                //Daha önce eklenmediyse ekle
                if (!h_userName_Follow.containsKey(h_userInfo_Follow.get("KullaniciAdi"))){
                    //Daha sonra kullanıcı adı ile yeni bir hashmap'e ekliyorum
                    h_userName_Follow.put(h_userInfo_Follow.get("KullaniciAdi"),h_userInfo_Follow);
                }
            }
        }

        try{
            ArrayList<String> kisiListesi=DataParsing.KisiListesiTespit(h_userName_Follow);
            ArrayList<String>kisiListesiProfilUrl=DataParsing.KullanıcıProfilResimleriTespit(h_userName_Follow);
            SharedPreferencesIslemleri.SharedPreferencesStringDizisiniKayitEt(context,kisiListesi, SharedPreferencesIslemleri.IslemTuru.TakipEdilen,kisiListesiProfilUrl);
        }catch (Exception e){
            Log.d("Error",e.getMessage());
        }

    }

}
