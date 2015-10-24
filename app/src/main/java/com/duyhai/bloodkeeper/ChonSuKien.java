package com.duyhai.bloodkeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import DTO.KHSuKienItem;
import bloodkeeperBus.KHSuKienBL;


public class ChonSuKien extends ActionBarActivity {

    private Toolbar toolbar;
    private KeHoachSuKienAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_su_kien);
        getFormWidget();
    }



    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.title_activity_chon_su_kien);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.drawerChonSuKien);
        adapter = new KeHoachSuKienAdapter(ChonSuKien.this,getData());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChonSuKien.this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ChonSuKien.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                chooseItem(position);
            }

            @Override
            public void onLongClick(View v, int position) {
                ///Viết hàm xử lý Click tại đây!!!
                Toast.makeText(ChonSuKien.this,"LongClick" + position,Toast.LENGTH_SHORT).show();
            }
        }));

    }

    public void chooseItem(int id) {
        Intent back = getIntent();
        back.putExtra("id", getData().get(id).title);
        back.putExtra("idKH", getData().get(id).ID);
        setResult(2, back);
        finish();
    }

    public List<KeHoachSuKienDetail> getData() {
        KHSuKienBL SKdb = new KHSuKienBL(this);
        List<KHSuKienItem> lst = SKdb.getAll();
        List<KeHoachSuKienDetail> data = new ArrayList<>();
        int size = lst.size();
        for (int i = 0; i < size; i++){
            KeHoachSuKienDetail cur = new KeHoachSuKienDetail(lst.get(i));
            data.add(cur);
        }
        return data;
    }

    //RecyclerView.OnItemTouchListener
    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private GestureDetector gestureDetector;
        private ClickListener clickListener;
        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener){
            Log.d("Kun", "Khởi tạo");
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("Kun","onSingleTapUp" + e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clickListener!=null ){
                        clickListener.onLongClick(child,recyclerView.getChildPosition(child));
                    }
                    Log.d("Kun","onLongPress" + e);
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
            Log.d("Kun","onTouchEvent" + e);
        }
    }

    public static interface ClickListener{
        public void onClick(View v, int position);
        public void onLongClick(View v, int position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chon_su_kien, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //ViewGroup group = (ViewGroup) findViewById(R.id.tblformSuKien);
        }

        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}