package com.duyhai.bloodkeeper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 5/24/2015.
 */
public class HuTienItemAdapter extends RecyclerView.Adapter<HuTienItemAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<HuTienDetail> data = Collections.emptyList();
    private Context context;

    HuTienItemAdapter(Context context, List<HuTienDetail> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.custom_row_hu_tien, parent ,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HuTienDetail current = data.get(position);
        //holder.icon.setImageResource(current.iconID);
        holder.iconimg.setImageResource(current.iconID);
        holder.title.setText(current.title);
        holder.informaton.setText(current.information);
        holder.tienchi.setText("-"+String.valueOf(current.tienchi));
        holder.tienthu.setText("+"+String.valueOf(current.tienthu));
        int valuepros = (int)((((float)current.tienthu - (float)current.tienchi)/(float)current.tienthu)*100);
        holder.proMoney.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
        holder.proMoney.setProgress(valuepros);
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
        ProgressBar proMoney;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.hutien_name);
            //icon = (ImageView) itemView.findViewById(R.id.hutien_icon);
            iconimg = (CircleImageView) itemView.findViewById(R.id.hutien_icon);
            informaton = (TextView) itemView.findViewById(R.id.hutien_infor);
            tienchi = (TextView) itemView.findViewById(R.id.tienchi_hutien);
            tienthu = (TextView) itemView.findViewById(R.id.tienthu_hutien);
            proMoney = (ProgressBar) itemView.findViewById(R.id.proMoney);
        }
    }
}
