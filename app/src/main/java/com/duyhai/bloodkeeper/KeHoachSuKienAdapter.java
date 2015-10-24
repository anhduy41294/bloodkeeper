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
 * Created by Duy on 6/21/2015.
 */
public class KeHoachSuKienAdapter extends RecyclerView.Adapter<KeHoachSuKienAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<KeHoachSuKienDetail> data = Collections.emptyList();
    private Context context;

    KeHoachSuKienAdapter(Context context, List<KeHoachSuKienDetail> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_kehoach_sukien, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        KeHoachSuKienDetail current = data.get(position);
        //holder.icon.setImageResource(current.iconID);
        holder.iconimg.setImageResource(current.iconID);
        holder.title.setText(current.title);
        holder.informaton.setText(current.information);
        holder.tienchi.setText("-" + String.valueOf(current.tienchi));
        holder.tienthu.setText("+" + String.valueOf(current.tienthu));
        if (current.apdung == true) {
            holder.apdung.setText("ĐANG ÁP DỤNG");
        } else {
            holder.apdung.setText("HẾT ÁP DỤNG");
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
        TextView tienchi;
        TextView tienthu;
        TextView apdung;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.khsk_name);
            //icon = (ImageView) itemView.findViewById(R.id.khsk_icon);
            iconimg = (CircleImageView) itemView.findViewById(R.id.khsk_icon);
            informaton = (TextView) itemView.findViewById(R.id.khsk_infor);
            tienchi = (TextView) itemView.findViewById(R.id.tienchi_khsk);
            tienthu = (TextView) itemView.findViewById(R.id.tienthu_khsk);
            apdung = (TextView) itemView.findViewById(R.id.apdung);

        }
    }
}
