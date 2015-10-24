package com.duyhai.bloodkeeper;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import DTO.KHTietKiemItem;
import DTO.VayNoItem;
import bloodkeeperBus.KHTietKiemBL;
import bloodkeeperBus.VayNoBL;

/**
 * Created by Duy on 6/21/2015.
 */
public class FragmentVayNo extends Fragment {
    private RecyclerView recyclerView;
    private VayNoAdapter adapter;
    public int kehoach;


    public void reload() {
        adapter = new VayNoAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_kehoach, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerListKeHoach);
        reload();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                ///Viết hàm xử lý Click tại đây!!!
                showDialog(position);
            }

            @Override
            public void onLongClick(View v, int position) {
                ///Viết hàm xử lý Click tại đây!!!
            }
        }));
        return layout;
    }

    int idV = 0;
    public void showDialog(final int index) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle("Chọn chức năng");
        b.setNeutralButton("Trả tiền", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                idV = index;
                Intent ii = new Intent(getActivity(), ThemTongTien.class);
                startActivityForResult(ii, 4);
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
                VayNoBL db = new VayNoBL(getActivity());
                db.deleteSoNo(getData().get(index).ID);
                //getActivity().finish();
                //startActivity(getActivity().getIntent());
                onResume();
            }
        });
        b.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 4) //intent them tong tien
        {
            switch (resultCode) {
                case 1:
                    int TienThem = data.getIntExtra("TongTien", 0);
                    VayNoBL db = new VayNoBL(getActivity());
                    VayNoItem item =  db.getNo(getData().get(idV).ID);
                    item.SoTienDaTra += TienThem;
                    db.updateSoNo(item.ID, item);
                    onResume();
                    //getActivity().finish();
                    //startActivity(getActivity().getIntent());
                    break;
                default: break;
            }
        }
    }

    public List<VayNoDetail> getData() {
        VayNoBL VayNodb = new VayNoBL(getActivity());
        List<VayNoItem> lst = VayNodb.getAllMuonNo();
        List<VayNoDetail> data = new ArrayList<>();
        int size = lst.size();
        for (int i = 0; i < size; i++){
            VayNoDetail cur = new VayNoDetail(lst.get(i));
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

}
