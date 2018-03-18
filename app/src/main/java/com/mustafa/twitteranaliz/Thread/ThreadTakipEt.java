package com.mustafa.twitteranaliz.Thread;

import android.util.Log;

import com.mustafa.twitteranaliz.twitterRest.MyTwitterApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;


public class ThreadTakipEt implements Runnable{

    String kullaniciAd;
    TwitterSession session;

    public ThreadTakipEt(String kullaniciAd, TwitterSession session) {
        this.kullaniciAd = kullaniciAd;
        this.session = session;
    }

    @Override
    public void run() {
        new MyTwitterApiClient(session).getTakipEt().takipEt(kullaniciAd)
                .enqueue(new Callback<User>() {
                    @Override
                    public void success(Result<User> result) {
                        Log.d("","");
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("","");
                    }
                });
    }
}