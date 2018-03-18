package com.mustafa.twitteranaliz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mustafa.twitteranaliz.AnalizSayfalari.Hayranlar;
import com.mustafa.twitteranaliz.AnalizSayfalari.TakipEtmeyenler;
import com.mustafa.twitteranaliz.GlobalBus.EventBusUserInfoSaveSharedPreferences;
import com.mustafa.twitteranaliz.GlobalBus.KullaniciBilgileriCekildi;
import com.mustafa.twitteranaliz.Thread.ThreadUserFollow;
import com.mustafa.twitteranaliz.Thread.ThreadUserFollowers;
import com.mustafa.twitteranaliz.Thread.UserIdVeriCekme;
import com.mustafa.twitteranaliz.class_.SharedPreferencesIslemleri;
import com.mustafa.twitteranaliz.ui.Adapter.KullaniciGenelBilgileriAdapter;
import com.mustafa.twitteranaliz.ui.AdapterClass.KullaniciGenelBilgileriAdapterClass;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TwitterSession session;

    RecyclerView recyclerViewUserInfo;
    TextView textView_SonGuncellemeZamani;
    Button btn_hayranlar,btn_takipEtmeyenler;

    ProgressBar progressBarMainActivity;

    private List<KullaniciGenelBilgileriAdapterClass> kullaniciGenelBilgileriAdapterClassList=new ArrayList<>();
    private KullaniciGenelBilgileriAdapter kullaniciGenelBilgileriAdapter;
    public Context context;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_action,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_yenile:
                // Butona basıldığında verielr yenilenir
                progressBarMainActivity.setVisibility(View.VISIBLE);
                UserIdVeriCekme userIdVeriCekme = new UserIdVeriCekme(session.getUserId(), session, getApplicationContext());
                userIdVeriCekme.run();

                ThreadUserFollow threadUserFollow=new ThreadUserFollow(session.getUserName(),"-1",session,getApplicationContext());
                threadUserFollow.run();

                ThreadUserFollowers threadUserFollowers=new ThreadUserFollowers(session.getUserName(),"-1",session,getApplicationContext());
                threadUserFollowers.run();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        this.setTitle(session.getUserName());

        progressBarMainActivity=(ProgressBar)findViewById(R.id.progressBar_mainActivity);
        progressBarMainActivity.setVisibility(View.INVISIBLE);
        btn_hayranlar=(Button) findViewById(R.id.button_hayranlar);
        btn_takipEtmeyenler=(Button) findViewById(R.id.button_takipEtmeyenler);
        // title kullanıcı ismi yapıldı

        try {
            progressBarMainActivity.setVisibility(View.VISIBLE);
            //Kayit yoksa bilgiler çekilecek
            if (!SharedPreferencesIslemleri.SharedPreferencesKayitKontrol(getApplicationContext(), "HomePageUserInfo")) {
                // Kullanıcı Verileri Çekiliyor.
                UserIdVeriCekme userIdVeriCekme = new UserIdVeriCekme(session.getUserId(), session, getApplicationContext());
                userIdVeriCekme.run();
            }
            // kullanıcı verilerini geri yükle
            else {
                try{
                    RecyclerViewUserInfoBilgileriGetir();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), R.string.toast_KullaniciVerileriCekilemedi,Toast.LENGTH_LONG).show();
                }
            }

            // Kullanıcı verileri çekiliyor ,progress bar kapanıyor oto 2 thread sonunda
            ThreadUserFollow threadUserFollow=new ThreadUserFollow(session.getUserName(),"-1",session,getApplicationContext());
            threadUserFollow.run();

            ThreadUserFollowers threadUserFollowers=new ThreadUserFollowers(session.getUserName(),"-1",session,getApplicationContext());
            threadUserFollowers.run();

        }catch (Exception e){
            progressBarMainActivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),R.string.toast_KullaniciVerileriCekilemedi,Toast.LENGTH_LONG).show();
        }

        btn_hayranlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Hayranlar.class);
                startActivity(intent);
            }
        });

        btn_takipEtmeyenler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, TakipEtmeyenler.class);
                startActivity(intent);
            }
        });
    }

//    // userid ile kullanıcı verileri sharedpreferences ile kaydedildikten sonra hata olmassa 1 dönüyor
//    @SuppressLint("HandlerLeak")
//    public final Handler userIdHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.arg1 == 1) {
//                try {
//                    RecyclerViewUserInfoBilgileriGetir();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), R.string.toast_VerilerYuklenemedi,Toast.LENGTH_LONG).show();
//                    Log.d("Error", e.getMessage());
//                }finally {
//                    // Bilgiler geldikten sonra veya hata olduğunda mutlaka progressbar kapanır
//                    progressBarMainActivity.setVisibility(View.INVISIBLE);
//                }
//                //kullanımı
//                Message msg=mainActivity.userIdHandler.obtainMessage();
//                msg.arg1=1;
//                mainActivity.userIdHandler.sendMessage(msg);
//            }
//        }
//    };

    @Subscribe
    public void onEvent(EventBusUserInfoSaveSharedPreferences event) {
        try {
            RecyclerViewUserInfoBilgileriGetir();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.toast_VerilerYuklenemedi,Toast.LENGTH_LONG).show();
            Log.d("Error", e.getMessage());
        }
    }

    @Subscribe
    public void onEvent(KullaniciBilgileriCekildi event){
        try {

            Toast.makeText(getApplicationContext(), R.string.toast_UserInfoVerilerYenilendi,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.toast_VerilerYuklenemedi,Toast.LENGTH_LONG).show();
            Log.d("Error", e.getMessage());
        }finally {
            // Bilgiler geldikten sonra veya hata olduğunda mutlaka progressbar kapanır
            progressBarMainActivity.setVisibility(View.INVISIBLE);
        }
    }

    public void RecyclerViewUserInfoBilgileriGetir() {
        textView_SonGuncellemeZamani = (TextView) findViewById(R.id.textView_son_guncelleme_degisken);
        recyclerViewUserInfo = (RecyclerView) findViewById(R.id.recyclerViewUserInfo);
        kullaniciGenelBilgileriAdapter= new KullaniciGenelBilgileriAdapter(kullaniciGenelBilgileriAdapterClassList);

        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewUserInfo.setLayoutManager(layoutManager);
        recyclerViewUserInfo.setAdapter(kullaniciGenelBilgileriAdapter);

        // listeleri dolduruyoruz.
        SharedPreferencesArrayListDoldurma();
    }

    private void SharedPreferencesArrayListDoldurma() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String preferencesOkunanDeger = preferences.getString("HomePageUserInfo", "-1");
        if (preferencesOkunanDeger.equals("-1")) {
            Toast.makeText(getApplicationContext(), R.string.toast_SharedPreferencesOkumaHatasıUserInfo,Toast.LENGTH_LONG).show();
            return;
        }
        String[] tempData=preferencesOkunanDeger.split("-");

        //son güncelleme zamanı işlendi
        textView_SonGuncellemeZamani.setText(tempData[5]);

        // Dublicate veri girişini engellemek için listeyi temizleyip dolduruyoruz.
        kullaniciGenelBilgileriAdapterClassList.clear();

        KullaniciGenelBilgileriAdapterClass adapterClass = new KullaniciGenelBilgileriAdapterClass(R.drawable.people,tempData[1],getResources().getStringArray(R.array.main_activity_recyclerViewAltYazilar)[0]);
        kullaniciGenelBilgileriAdapterClassList.add(adapterClass);

        adapterClass=new KullaniciGenelBilgileriAdapterClass(R.drawable.people,tempData[3],getResources().getStringArray(R.array.main_activity_recyclerViewAltYazilar)[1]);
        kullaniciGenelBilgileriAdapterClassList.add(adapterClass);

        adapterClass=new KullaniciGenelBilgileriAdapterClass(R.drawable.people,tempData[2],getResources().getStringArray(R.array.main_activity_recyclerViewAltYazilar)[2]);
        kullaniciGenelBilgileriAdapterClassList.add(adapterClass);

        adapterClass=new KullaniciGenelBilgileriAdapterClass(R.drawable.people,tempData[4],getResources().getStringArray(R.array.main_activity_recyclerViewAltYazilar)[3]);
        kullaniciGenelBilgileriAdapterClassList.add(adapterClass);

        kullaniciGenelBilgileriAdapter.notifyDataSetChanged();
    }

}
