package com.mustafa.twitteranaliz.ui.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mustafa.twitteranaliz.R;
import com.mustafa.twitteranaliz.ui.AdapterClass.KullaniciGenelBilgileriAdapterClass;

import java.util.List;

public class KullaniciGenelBilgileriAdapter extends RecyclerView.Adapter<KullaniciGenelBilgileriAdapter.MyViewHolder>{

    private List<KullaniciGenelBilgileriAdapterClass> kullaniciGenelBilgileriAdapterClassList;

    public KullaniciGenelBilgileriAdapter(List<KullaniciGenelBilgileriAdapterClass> kullaniciGenelBilgileriAdapterClassList) {
        this.kullaniciGenelBilgileriAdapterClassList = kullaniciGenelBilgileriAdapterClassList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_listview_main_activity_user_info,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KullaniciGenelBilgileriAdapterClass adapterClass=kullaniciGenelBilgileriAdapterClassList.get(position);
        holder.holder_AltMetin.setText(adapterClass.getBilgilendirmeMetni());
        holder.holder_Image.setImageResource(Integer.parseInt(String.valueOf(adapterClass.getUstResim())));
        holder.holder_SayiMetni.setText(adapterClass.getSayisalBilgi());
    }

    @Override
    public int getItemCount() {
        return kullaniciGenelBilgileriAdapterClassList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView holder_AltMetin,holder_SayiMetni;
        ImageView holder_Image;

        public MyViewHolder(View itemView) {
            super(itemView);
            holder_AltMetin=(TextView)itemView.findViewById(R.id.userInfo_recyclerView_altBilgi);
            holder_SayiMetni=(TextView)itemView.findViewById(R.id.userInfo_recyclerView_Sayilar);
            holder_Image=(ImageView)itemView.findViewById(R.id.userInfo_recyclerView_Image);
        }
    }

}
