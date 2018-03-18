package com.mustafa.twitteranaliz.AnalizSayfalari;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mustafa.twitteranaliz.GlobalBus.KullaniciBilgileriCekildi;
import com.mustafa.twitteranaliz.R;
import com.mustafa.twitteranaliz.Thread.ThreadTakipEt;
import com.mustafa.twitteranaliz.class_.TwitterVeriAnaliz;
import com.mustafa.twitteranaliz.ui.Adapter.HayranlarAdapter;
import com.mustafa.twitteranaliz.ui.AdapterClass.HayranlarAdapterClass;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hayranlar extends AppCompatActivity implements Runnable{

    private List<HayranlarAdapterClass> hayranlarAdapterClassesList = new ArrayList<>();
    private HayranlarAdapter hayranlarAdapter;
    public Context context;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TwitterSession session;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        progressBar=(ProgressBar)findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        Thread threadHayranlar = new Thread(Hayranlar.this);
        threadHayranlar.run();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hayranlar);
        context=getApplicationContext();
        recyclerView=(RecyclerView)findViewById(R.id.Hayranlar_RecyclerView);

        hayranlarAdapter=new HayranlarAdapter(hayranlarAdapterClassesList,new HayranlarAdapter.HayranlarTakipBirakListener(){
            @Override
            public void takipEtButonClick(View v, final int position) {
                try{
                    if (hayranlarAdapterClassesList.get(position).getButonYazi().trim().equals(context.getString(R.string.TakipEt))){
                        ThreadTakipEt threadTakipEt=new ThreadTakipEt(hayranlarAdapterClassesList.get(position).getKullaniciAd(),session);
                        threadTakipEt.run();
                    }
                }catch (Exception e){
                    Log.d("e",e.getMessage());
                }
            }
        });

    }

    @Subscribe
    public void onEvent(KullaniciBilgileriCekildi event){
        try {
//            if (!hayranlarAdapterClassesList.get(position).getButonYazi().equals(context.getString(R.string.TakipEt))){
//                if (hayranlarAdapterClassesList.get(position).getButonYazi().equals("...")){
//                    return;
//                }
//
//                AlertDialog.Builder builder=new AlertDialog.Builder(context);
//                builder.setMessage(R.string.geriTakipEtmekIstedigineEminMisin);
//                builder.setCancelable(true);
//                builder.setPositiveButton(
//                        getString(R.string.Evet),
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                hayranlarAdapterClassesList.get(position).setButonYazi("...");
//                                ThreadTakipEt threadTakipEt=new ThreadTakipEt(hayranlarAdapterClassesList.get(position).getKullaniciAd(),session);
//                                threadTakipEt.run();
//                            }
//                        }
//                );
//                builder.show();
//            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.toast_VerilerYuklenemedi,Toast.LENGTH_LONG).show();
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public void run() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    HashMap<String,String> HashmapVeri;
                    HashmapVeri = TwitterVeriAnaliz.HayranlariBul(getApplicationContext());

                    layoutManager=new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(hayranlarAdapter);

                    HayranlarAdapterClass hayranlarAdapterClass;
                    for(Map.Entry<String,String> entry:HashmapVeri.entrySet()){
                        hayranlarAdapterClass=new HayranlarAdapterClass(entry.getValue(),entry.getKey(),context.getString(R.string.TakipEt),context);
                        hayranlarAdapterClassesList.add(hayranlarAdapterClass);
                    }
                    hayranlarAdapter.notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),context.getString(R.string.throw_KayitliVerilerOkunamadi),Toast.LENGTH_LONG).show();
                }finally {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
