package com.duyhai.bloodkeeper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 6/21/2015.
 */
public class KeHoachTietKiemAdapter extends RecyclerView.Adapter<KeHoachTietKiemAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<KeHoachTietKiemDetail> data = Collections.emptyList();
    private Context context;

    KeHoachTietKiemAdapter(Context context, List<KeHoachTietKiemDetail> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_kehoach_tietkiem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KeHoachTietKiemDetail current = data.get(position);
        //holder.icon.setImageResource(current.iconID);
        holder.iconimg.setImageResource(current.iconID);
        holder.title.setText(current.title);
        holder.informaton.setText(current.information);
        holder.tienhienco.setText(String.valueOf(current.tienhienco));
        holder.tienmuctieu.setText(String.valueOf(current.tienmuctieu));
        Date d = new Date();
        if (current.ngaykethuc == "") {
            if (current.tienhienco >= current.tienmuctieu){
                holder.ngayketthuc.setTextColor(R.color.colorAccent);
                holder.ngayketthuc.setText("HOÀN THÀNH");
            }else{
                holder.ngayketthuc.setTextColor(R.color.colorMoneySub);
                holder.ngayketthuc.setText("HẾT HẠN");
            }

        } else {
            if (current.tienhienco >= current.tienmuctieu){
                holder.ngayketthuc.setTextColor(R.color.colorAccent);
                holder.ngayketthuc.setText("HOÀN THÀNH");
            }else{
                holder.ngayketthuc.setTextColor(R.color.colorDivider);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                holder.ngayketthuc.setText(current.ngaykethuc);
            }
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        CircleImageView iconimg;
        TextView informaton;
        TextView tienhienco;
        TextView tienmuctieu;
        TextView ngayketthuc;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.khtk_name);
            //icon = (ImageView) itemView.findViewById(R.id.khtk_icon);
            iconimg = (CircleImageView) itemView.findViewById(R.id.khtk_icon);
            informaton = (TextView) itemView.findViewById(R.id.khtk_infor);
            tienhienco = (TextView) itemView.findViewById(R.id.tienhienco_khtk);
            tienmuctieu = (TextView) itemView.findViewById(R.id.tienmuctieu_khtk);
            ngayketthuc = (TextView) itemView.findViewById(R.id.ngayketthuc);

        }
    }
}
