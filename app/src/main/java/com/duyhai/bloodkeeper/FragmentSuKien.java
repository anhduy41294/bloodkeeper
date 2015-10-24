package com.duyhai.bloodkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DTO.KHSuKienItem;
import bloodkeeperBus.KHSuKienBL;
import bloodkeeperBus.KHTietKiemBL;

/**
 * Created by Duy on 6/21/2015.
 */
public class FragmentSuKien extends Fragment {
    private RecyclerView recyclerView;
    private KeHoachSuKienAdapter adapter;

    private KHSuKienBL SKdb;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Load();
        View layout = inflater.inflate(R.layout.fragment_kehoach, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerListKeHoach);
        adapter = new KeHoachSuKienAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                showDialog(position);
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));
        return layout;
    }

    public void showDialog(final int id) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle("Chọn chức năng");
        b.setNeutralButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                KeHoachSuKienDetail item = getData().get(id);
                item.apdung = !item.apdung;
                KHSuKienBL bd = new KHSuKienBL(getActivity());
                bd.updateSK(item.ID, new KHSuKienItem(item));
                onResume();
                //getActivity().finish();
                //startActivity(getActivity().getIntent());
            }
        });
        b.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        b.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                KHSuKienBL db = new KHSuKienBL(getActivity());
                db.deleteSK(getData().get(id).ID);
                onResume();
                //getActivity().finish();
                //startActivity(getActivity().getIntent());
            }
        });
        b.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new KeHoachSuKienAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
    }

    public List<KeHoachSuKienDetail> getData() {
        List<KHSuKienItem> lst = SKdb.getAll();
        List<KeHoachSuKienDetail> data = new ArrayList<>();
        int size = lst.size();
        for (int i = 0; i < size; i++){
            KeHoachSuKienDetail cur = new KeHoachSuKienDetail(lst.get(i));
            data.add(cur);
        }
        return data;
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null ){
                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clickListener!=null &&gestureDetector.onTouchEvent(e) ){
                clickListener.onClick(child,rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
    }

    public static interface ClickListener{
        public void onClick(View v, int position);
        public void onLongClick(View v, int position);
    }

    public void Load() {
        SKdb = new KHSuKienBL(getActivity());
    }
}
