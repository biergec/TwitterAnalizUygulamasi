package com.mustafa.twitteranaliz.ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mustafa.twitteranaliz.R;
import com.mustafa.twitteranaliz.ui.AdapterClass.TakipEtmeyenlerAdaterClass;

import java.util.List;

public class TakipEtmeyenlerAdapter extends RecyclerView.Adapter<TakipEtmeyenlerAdapter.MyViewHolder>{

    private List<TakipEtmeyenlerAdaterClass> takipEtmeyenlerAdaterClassList;
    private TakipEtmeyenlerTakipBirakListener onClickListener;

    public TakipEtmeyenlerAdapter(List<TakipEtmeyenlerAdaterClass> takipEtmeyenlerAdaterClassList, TakipEtmeyenlerTakipBirakListener onClickListener) {
        this.takipEtmeyenlerAdaterClassList = takipEtmeyenlerAdaterClassList;
        this.onClickListener = onClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kullanici_listesi,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TakipEtmeyenlerAdaterClass adaterClass=takipEtmeyenlerAdaterClassList.get(position);
        holder.h_buton_text.setText(adaterClass.getButonYazi());
        holder.h_kullanici_ad.setText(adaterClass.getKullaniciAd());
        String url = adaterClass.getResimURL();
        Glide.with(adaterClass.getContext()).load(url).into(holder.h_kullanici_resmi);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView h_kullanici_ad;
        Button h_buton_text;
        ImageView h_kullanici_resmi;

        MyViewHolder(final View itemView) {
            super(itemView);
            h_kullanici_ad=(TextView)itemView.findViewById(R.id.textView_ust_kullanici_adi);
            h_buton_text=(Button)itemView.findViewById(R.id.button_takip_Et_takibi_birak);
            h_kullanici_resmi=(ImageView)itemView.findViewById(R.id.imageView_profil_resim);

            h_buton_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TakipEtmeyenlerAdaterClass adapterClass=takipEtmeyenlerAdaterClassList.get(getAdapterPosition());
                    onClickListener.takipBirakButonClick(v,getAdapterPosition());
                    adapterClass.setButonYazi("Takipten Çıkarıldı");
                }
            });
        }
    }

    public interface TakipEtmeyenlerTakipBirakListener{
        void takipBirakButonClick(View v, int position);
    }

}
