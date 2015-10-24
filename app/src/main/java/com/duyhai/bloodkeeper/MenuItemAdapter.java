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

/**
 * Created by Duy on 5/21/2015.
 */
public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    List<Information> data = Collections.emptyList();
    private Context context;
    //private ClickListener clickListener;
    public MenuItemAdapter(Context context, List<Information> data){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_row_item_menu, viewGroup ,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int i) {
        Information current = data.get(i);
        viewHolder.title.setText(current.title);
        viewHolder.icon.setImageResource(current.iconID);
    }
    //Demo Delete
    /*public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }*/
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
            //itemView.setOnClickListener(this);
        }


        /*@Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, SubActivity.class));
            if(clickListener!=null){
                clickListener.itemClicked(v,getPosition());
            }
        }*/
    }

    /*public interface ClickListener{
        public void itemClicked(View view, int position);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }*/
}
