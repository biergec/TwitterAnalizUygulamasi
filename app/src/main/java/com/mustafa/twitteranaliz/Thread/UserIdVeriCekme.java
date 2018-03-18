package com.mustafa.twitteranaliz.Thread;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mustafa.twitteranaliz.GlobalBus.EventBusUserInfoSaveSharedPreferences;
import com.mustafa.twitteranaliz.class_.SharedPreferencesIslemleri;
import com.mustafa.twitteranaliz.twitterRest.MyTwitterApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserIdVeriCekme implements Runnable {

    private Long userId;
    private TwitterSession session;
    private Context context;

    public UserIdVeriCekme(Long userId, TwitterSession session, Context context) {
        this.userId = userId;
        this.session = session;
        this.context = context;
    }

    @Override
    public void run() {
        new MyTwitterApiClient(session).getCustomService().show(session.getUserId())
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        try{
                            SharedPreferencesKayitEkle(result);
                            EventBus.getDefault().post(new EventBusUserInfoSaveSharedPreferences(true));
                        }catch (Exception e){
                            Log.d("Error",e.getMessage());
                        }
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("Error",exception.getMessage());
                    }
                });
    }


    private void SharedPreferencesKayitEkle(Result<User> result) {
        try {
            Calendar cal = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

            if (SharedPreferencesIslemleri.SharedPreferencesKayitKontrol(context,"HomePageUserInfo")){
                SharedPreferencesIslemleri.SharedPreferencesKayitSilme(context,"HomePageUserInfo");
            }
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor=preferences.edit();

            editor.putString("HomePageUserInfo",
                    result.data.name+"-"+
                            result.data.followersCount+"-"+
                            result.data.friendsCount+"-"+
                            result.data.statusesCount+"-"+
                            result.data.favouritesCount+"-"+
                            sdf.format(cal.getTime())+"-"+
                            result.data.profileImageUrl
            );
            editor.apply();
        }catch (Exception e){
            Log.d("Eror",e.getMessage());
        }
    }
}
