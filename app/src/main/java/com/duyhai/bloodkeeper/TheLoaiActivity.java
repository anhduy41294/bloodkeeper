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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import DTO.TheLoaiItem;
import bloodkeeperBus.TheLoaiBL;


public class TheLoaiActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private TheLoaiAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        getFormWidget();
        addEvent();
    }



    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.title_activity_the_loai);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.drawerListTheLoai);
        adapter = new TheLoaiAdapter(TheLoaiActivity.this,getData());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(TheLoaiActivity.this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(TheLoaiActivity.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                ///Viết hàm xử lý Click tại đây!!!
                chooseItem(position);
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));
    }

    public void chooseItem(int id) {
        Intent back = getIntent();
        back.putExtra("ten", getData().get(id).TenTheLoai);
        back.putExtra("idTL", getData().get(id).ID);
        back.putExtra("icon", getData().get(id).icon);
        setResult(1, back);
        finish();
    }


    private void addEvent() {

    }

    public List<TheLoaiDetail> getData(){
        TheLoaiBL db = new TheLoaiBL(this);
        List<TheLoaiItem> lst = db.getAll();
        int size = lst.size();
        List<TheLoaiDetail> data = new ArrayList<>();
        for (int i = 0; i < size; i++){
            TheLoaiDetail cur = new TheLoaiDetail(lst.get(i));
            data.add(cur);
        }
        return data;
    }

    //RecyclerView.OnItemTouchListener
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sub, menu);
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
            ViewGroup group = (ViewGroup) findViewById(R.id.tblformSuKien);
        }

        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
