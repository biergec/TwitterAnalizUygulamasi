package com.mustafa.twitteranaliz.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.mustafa.twitteranaliz.ConnectionDetector;
import com.mustafa.twitteranaliz.MainActivity;
import com.mustafa.twitteranaliz.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.Response;

public class login extends AppCompatActivity {

    private TwitterLoginButton loginButton;
    TwitterSession session;

    private boolean InternetKontrol(){
        ConnectionDetector connectionDetector=new ConnectionDetector(getApplicationContext());
        if (connectionDetector.isConnectingToInternet()){
            Log.d("login","İnternet bağlantısı başarılı");
            return true;
        }else{
            Toast.makeText(getApplicationContext(),getString(R.string.toast_internetBaglatisiYok),Toast.LENGTH_LONG).show();
            Log.d("login","İnternet bağlantısı başarısız");
            return false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // internet varmı? varsa twitter login durumuna bak. Aktar.
        if (InternetKontrol()){
            TwitterLoginKontrol();
        }else
            Toast.makeText(getApplicationContext(),"Lütfen İnternet Bağlantınızı Kontrol Ediniz!",Toast.LENGTH_LONG).show();
    }

    // login olmuşşa kullanıcı direk main menü aktarır.
    private void TwitterLoginKontrol() {
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session!=null){
            Intent intent_twitter_session=new Intent(this,MainActivity.class);
            startActivity(intent_twitter_session);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Twitter.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterGiris(result);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(), R.string.toast_islem_basarisiz,Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    public void TwitterGiris(final Result<TwitterSession> result) {
        final TwitterSession session = result.data;

        TwitterCore.getInstance().getApiClient(session).getAccountService().verifyCredentials(true, false, true).enqueue(new retrofit2.Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    //Kullanıcı Giriş Yaptığında ana sayfaya yönlendirilecek
                    TwitterLoginKontrol();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.toast_giris_basarisiz,Toast.LENGTH_LONG).show();
            }
        });
    }

}
