package com.mustafa.twitteranaliz.Thread;

import com.mustafa.twitteranaliz.twitterRest.MyTwitterApiClient;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class ThreadTakipBirak implements Runnable {

    String kullaniciAd;
    TwitterSession session;

    public ThreadTakipBirak(String kullaniciAd, TwitterSession session) {
        this.kullaniciAd = kullaniciAd;
        this.session = session;
    }

    @Override
    public void run() {
        new MyTwitterApiClient(session).getTakipBirak().takipBirak(kullaniciAd)
                .enqueue(new Callback<com.twitter.sdk.android.core.models.User>() {
                    @Override
                    public void success(Result<com.twitter.sdk.android.core.models.User> result) {

                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                });
    }
}
