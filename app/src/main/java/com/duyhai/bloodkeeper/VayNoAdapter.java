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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 6/21/2015.
 */
public class VayNoAdapter extends RecyclerView.Adapter<VayNoAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<VayNoDetail> data = Collections.emptyList();
    private Context context;

    VayNoAdapter(Context context, List<VayNoDetail> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_vayno, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        VayNoDetail current = data.get(position);
        //holder.icon.setImageResource(current.iconID);
        holder.iconimg.setImageResource(current.iconID);
        holder.title.setText(current.title);
        holder.informaton.setText(current.information);
        holder.tienhienco.setText(String.valueOf(current.tiendatra));
        holder.tienmuctieu.setText(String.valueOf(current.tiencantra));
        Date d = new Date();
        if (current.ngaykethuc == "") {
            if (current.tiendatra >= current.tiencantra){
                holder.ngayketthuc.setTextColor(R.color.colorAccent);
                holder.ngayketthuc.setText("HOÀN THÀNH");
            }else{
                holder.ngayketthuc.setTextColor(R.color.colorMoneySub);
                holder.ngayketthuc.setText("HẾT HẠN");
            }

        } else {
            if (current.tiendatra >= current.tiencantra){
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
            title = (TextView) itemView.findViewById(R.id.vayno_name);
            //icon = (ImageView) itemView.findViewById(R.id.vayno_icon);
            iconimg = (CircleImageView) itemView.findViewById(R.id.vayno_icon);
            informaton = (TextView) itemView.findViewById(R.id.vayno_infor);
            tienhienco = (TextView) itemView.findViewById(R.id.tienhienco_vayno);
            tienmuctieu = (TextView) itemView.findViewById(R.id.tienmuctieu_vayno);
            ngayketthuc = (TextView) itemView.findViewById(R.id.ngayketthuc);

        }
    }
}
