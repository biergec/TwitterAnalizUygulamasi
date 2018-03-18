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

import com.mustafa.twitteranaliz.R;
import com.mustafa.twitteranaliz.Thread.ThreadTakipBirak;
import com.mustafa.twitteranaliz.class_.TwitterVeriAnaliz;
import com.mustafa.twitteranaliz.ui.Adapter.TakipEtmeyenlerAdapter;
import com.mustafa.twitteranaliz.ui.AdapterClass.TakipEtmeyenlerAdaterClass;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TakipEtmeyenler extends AppCompatActivity implements Runnable {

    private List<TakipEtmeyenlerAdaterClass> takipEtmeyenlerAdaterClassList = new ArrayList<>();
    private TakipEtmeyenlerAdapter takipEtmeyenlerAdapter;
    public Context context;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    TwitterSession session;

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        Thread threadTakipEtmeyenler = new Thread(TakipEtmeyenler.this);
        threadTakipEtmeyenler.run();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takip_etmeyenler);
        context=getApplicationContext();
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView_takipEtmeyenler);

        takipEtmeyenlerAdapter=new TakipEtmeyenlerAdapter(takipEtmeyenlerAdaterClassList,new TakipEtmeyenlerAdapter.TakipEtmeyenlerTakipBirakListener(){
            @Override
            public void takipBirakButonClick(View v, int position) {
                try{
                    if (takipEtmeyenlerAdaterClassList.get(position).getButonYazi().trim().equals(context.getString(R.string.TakipEt))){
                        if (takipEtmeyenlerAdaterClassList.get(position).getButonYazi().equals("...")){
                            return;
                        }
                        ThreadTakipBirak threadTakipEt=new ThreadTakipBirak(takipEtmeyenlerAdaterClassList.get(position).getKullaniciAd(),session);
                        threadTakipEt.run();
                    }
                }catch (Exception e){
                    Log.d("e",e.getMessage());
                }
            }
        });

    }

    @Override
    public void run() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try{
                HashMap<String,String> HashmapVeri;
                HashmapVeri = TwitterVeriAnaliz.TakipEtmeyenleriBul(getApplicationContext());

                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(takipEtmeyenlerAdapter);

                TakipEtmeyenlerAdaterClass takipEtmeyenlerAdaterClass;
                for(Map.Entry<String,String> entry:HashmapVeri.entrySet()){
                    takipEtmeyenlerAdaterClass=new TakipEtmeyenlerAdaterClass(entry.getValue(),entry.getKey(),context.getString(R.string.TakipEt),context);
                    takipEtmeyenlerAdaterClassList.add(takipEtmeyenlerAdaterClass);
                }
                takipEtmeyenlerAdapter.notifyDataSetChanged();

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
