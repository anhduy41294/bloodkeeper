package com.duyhai.bloodkeeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 6/21/2015.
 */
public class GiaoDichAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater layoutInflater;
    List<IGiaoDichItem> data = Collections.emptyList();
    private Context context;

    GiaoDichAdapter(Context context, List<IGiaoDichItem> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        IGiaoDichItem current = data.get(position);
        return current.getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view = layoutInflater.inflate(R.layout.custom_row_header_giaodich, parent ,false);
            MyViewHolder1 myViewHolder = new MyViewHolder1(view);
            return myViewHolder;
        }else {
            View view = layoutInflater.inflate(R.layout.custom_row_giaodich, parent ,false);
            MyViewHolder2 myViewHolder = new MyViewHolder2(view);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IGiaoDichItem current = data.get(position);
        if (current.getViewType()==0){
            HeaderGiaoDich curr = (HeaderGiaoDich) current;
            MyViewHolder1 holdercur = (MyViewHolder1) holder;
            holdercur.title.setText(curr.content);
            holdercur.tien.setText(String.valueOf(curr.tien));
            if (curr.trangthai == false){
                holdercur.tien.setTextColor(Color.RED);
            }else{
                holdercur.tien.setTextColor(Color.BLUE);
            }
        }else{
            GiaoDichDetail curr = (GiaoDichDetail) current;
            MyViewHolder2 holdercur = (MyViewHolder2) holder;
            holdercur.title.setText(curr.TenGiaoDich);
            holdercur.tien.setText(String.valueOf(curr.Money));
            holdercur.informaton.setText(curr.Note);
            holdercur.iconimg.setImageResource(curr.HinhTheLoai);
            holdercur.theloai.setText(curr.TenTheLoai);
            if (curr.Type == false){
                holdercur.tien.setTextColor(Color.RED);
            }else{
                holdercur.tien.setTextColor(Color.BLUE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder {

        TextView title;
        TextView tien;
        RelativeLayout layout;
        public MyViewHolder1(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.headerNgayGiaoDich);
            tien = (TextView) itemView.findViewById(R.id.tiendutrongngay);
        }
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {

        TextView title;
        CircleImageView iconimg;
        TextView informaton;
        TextView tien;
        TextView theloai;
        public MyViewHolder2(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.giaodich_name);
            iconimg = (CircleImageView) itemView.findViewById(R.id.giaodich_icon);
            informaton = (TextView) itemView.findViewById(R.id.giaodich_infor);
            tien = (TextView) itemView.findViewById(R.id.giaodich_tien);
            theloai = (TextView) itemView.findViewById(R.id.txttheloaigiaodich);

        }
    }
}