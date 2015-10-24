package com.duyhai.bloodkeeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 6/24/2015.
 */
public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.MyViewHolder>{

    private LayoutInflater layoutInflater;
    List<TheLoaiDetail> data= Collections.emptyList();
    private Context context;

    TheLoaiAdapter(Context context,List<TheLoaiDetail>data){
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view=layoutInflater.inflate(R.layout.custom_row_theloai,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,int position){
        TheLoaiDetail current=data.get(position);
        holder.iconimg.setImageResource(current.icon);
        holder.title.setText(current.TenTheLoai);
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        CircleImageView iconimg;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.theloai_name);
            iconimg = (CircleImageView) itemView.findViewById(R.id.theloai_icon);
        }
    }
}