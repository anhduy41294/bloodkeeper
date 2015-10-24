package com.duyhai.bloodkeeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 6/24/2015.
 */
public class TheLoaiThongKeAdapter extends RecyclerView.Adapter<TheLoaiThongKeAdapter.MyViewHolder>{

    private LayoutInflater layoutInflater;
    List<TheLoaiThongKeDetails> data= Collections.emptyList();
    private Context context;

    TheLoaiThongKeAdapter(Context context,List<TheLoaiThongKeDetails>data){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=layoutInflater.inflate(R.layout.custom_theloai_thongke,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        TheLoaiThongKeDetails current=data.get(position);
        holder.iconimg.setBackgroundColor(current.color);
        holder.title.setText(current.TenTheLoai);
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView iconimg;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.theloai_thongke_name);
            iconimg = (ImageView) itemView.findViewById(R.id.theloai_thongke_color);
        }
    }
}
