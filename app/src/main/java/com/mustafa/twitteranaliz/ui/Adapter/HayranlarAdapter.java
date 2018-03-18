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
import com.mustafa.twitteranaliz.ui.AdapterClass.HayranlarAdapterClass;

import java.util.List;

public class HayranlarAdapter extends RecyclerView.Adapter<HayranlarAdapter.MyViewHolder> {

    private List<HayranlarAdapterClass> hayranList;
    private HayranlarTakipBirakListener onClickListener;

    public HayranlarAdapter(List<HayranlarAdapterClass> hayranList, HayranlarTakipBirakListener onClickListener) {
        this.hayranList = hayranList;
        this.onClickListener = onClickListener;
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
                    HayranlarAdapterClass adapterClass=hayranList.get(getAdapterPosition());
                    hayranList.get(getAdapterPosition()).setButonYazi("Takip Edildi");
                    onClickListener.takipEtButonClick(v,getAdapterPosition());
                }
            });
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kullanici_listesi,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HayranlarAdapterClass adapterClass=hayranList.get(position);
        holder.h_buton_text.setText(adapterClass.getButonYazi());
        holder.h_kullanici_ad.setText(adapterClass.getKullaniciAd());
        String url = adapterClass.getResimURL();
        Glide.with(adapterClass.getContext()).load(url).into(holder.h_kullanici_resmi);
    }

    @Override
    public int getItemCount() {
        return hayranList.size();
    }

    public interface HayranlarTakipBirakListener {
        void takipEtButonClick(View v, int position);
    }
}


