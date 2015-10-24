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

import DAO.KHTietKiemDAO;
import DTO.KHTietKiemItem;
import bloodkeeperBus.KHTietKiemBL;

/**
 * Created by HaiTuan on 6/24/2015.
 */
public class FragmentTietKiem extends Fragment {
    private RecyclerView recyclerView;
    private KeHoachTietKiemAdapter adapterTK;

    private KHTietKiemDAO TKdb;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Load();
        View layout = inflater.inflate(R.layout.fragment_kehoach, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerListKeHoach);
        adapterTK = new KeHoachTietKiemAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapterTK);
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

    int idV = 0;
    public void showDialog(final int id) {
        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        b.setTitle("Chọn chức năng");
        b.setNeutralButton("Thêm tiền", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                idV = id;
                Intent ii = new Intent(getActivity(), ThemTongTien.class);
                startActivityForResult(ii, 2);
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
                KHTietKiemBL db = new KHTietKiemBL(getActivity());
                db.deleteSK(getData().get(id).ID);
                onResume();
                //getActivity().finish();
                //startActivity(getActivity().getIntent());
            }
        });
        b.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2) //intent them tong tien
        {
            switch (resultCode) {
                case 1:
                    int TienThem = data.getIntExtra("TongTien", 0);
                    KHTietKiemItem item = new KHTietKiemItem(getData().get(idV));
                    item.tienhienco += TienThem;
                    KHTietKiemBL db = new KHTietKiemBL(getActivity());
                    db.updateSK(item.ID, item);
                    onResume();
                    //getActivity().finish();
                    //startActivity(getActivity().getIntent());
                    break;
                default: break;
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterTK = new KeHoachTietKiemAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapterTK);
    }

    public List<KeHoachTietKiemDetail> getData() {
        List<KHTietKiemItem> lst = TKdb.getAllKHTK();
        List<KeHoachTietKiemDetail> data = new ArrayList<>();
        int size = lst.size();
        for (int i = 0; i < size; i++){
            KeHoachTietKiemDetail cur = new KeHoachTietKiemDetail(lst.get(i));
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
        TKdb = new KHTietKiemDAO(getActivity());
    }
}
