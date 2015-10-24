package com.duyhai.bloodkeeper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import DAO.TheLoaiDAO;
import DTO.GiaoDichItem;
import DTO.TheLoaiItem;
import bloodkeeperBus.GiaoDichBL;


public class ThongKeActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private PieGraph pg;
    private TheLoaiThongKeAdapter adapter;
    private RecyclerView recyclerView;

    private GiaoDichBL GDTKdb;
    private TheLoaiDAO TKdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Load();
        setContentView(R.layout.activity_thong_ke);
        getFormWidget();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thong_ke, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_timkiem) {
            return true;
        }
        if(id == R.id.action_search){
            //code tại đây
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);



        long[] tien = tienTheoTheLoai();
        pg = (PieGraph)findViewById(R.id.graph);
        if(tien[0] == 0) defaultCode();
        else thongke(tien);


        recyclerView = (RecyclerView) findViewById(R.id.drawerListTheLoaiTK);
        adapter = new TheLoaiThongKeAdapter(ThongKeActivity.this,getData());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ThongKeActivity.this));
    }

    private void addEvent() {

    }

    public void thongke(long[] tien) {
        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setValue(tien[0]);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(tien[1]);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(tien[2]);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#0000FF"));
        slice.setValue(tien[3]);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FF00FF"));
        slice.setValue(tien[4]);
        pg.addSlice(slice);
    }

    public void defaultCode() {
        PieSlice slice = new PieSlice();
        slice.setColor(Color.parseColor("#99CC00"));
        slice.setTitle("aa");
        slice.setValue(5);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FFBB33"));
        slice.setValue(5);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#AA66CC"));
        slice.setValue(5);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#0000FF"));
        slice.setValue(5);
        pg.addSlice(slice);
        slice = new PieSlice();
        slice.setColor(Color.parseColor("#FF00FF"));
        slice.setValue(5);
        pg.addSlice(slice);
    }

    public long[] tienTheoTheLoai() {
        List<TheLoaiItem> lstTL = TKdb.getAllTheLoai();
        int size = lstTL.size();
        long[] tienTheoTL = new long[size];
        for(int i = 0; i < size; ++i) {
            List<GiaoDichItem> lstgd = GDTKdb.getTheoTheLoai(lstTL.get(i).ID);
            int count = lstgd.size();
            long tien = 0;
            for(int j = 0; j < count; ++j) {
                tien += lstgd.get(j).SoTien;
            }
            tienTheoTL[i] = tien;
        }
        return tienTheoTL;
    }

    public List<TheLoaiThongKeDetails> getData(){
        List<TheLoaiThongKeDetails> data = new ArrayList<>();
        TheLoaiThongKeDetails GD = new TheLoaiThongKeDetails("Gia đình",Color.parseColor("#99CC00"));
        data.add(GD);
        TheLoaiThongKeDetails TY = new TheLoaiThongKeDetails("Tình Yêu",Color.parseColor("#FFBB33"));
        data.add(TY);
        TheLoaiThongKeDetails HT = new TheLoaiThongKeDetails("Học Tập",Color.parseColor("#AA66CC"));
        data.add(HT);
        TheLoaiThongKeDetails AU = new TheLoaiThongKeDetails("Ăn uống",Color.parseColor("#0000FF"));
        data.add(AU);
        TheLoaiThongKeDetails GM = new TheLoaiThongKeDetails("Game",Color.parseColor("#FF00FF"));
        data.add(GM);
        return data;
    }

    public void Load() {
        GDTKdb = new GiaoDichBL(this);
        TKdb = new TheLoaiDAO(this);
    }
}
