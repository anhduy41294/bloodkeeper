package com.duyhai.bloodkeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import DAO.DanhMucDAO;
import DTO.HuTienItem;
import bloodkeeperBus.HuTienBL;
import bloodkeeperBus.TheLoaiBL;

public class Dashboard extends ActionBarActivity {

    public static boolean l = false;
    private Toolbar toolbar;


    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;

    private static int TongTien = 0;

    public void loadingdatabase() {
        if(!l) {
            l = true;
            LoadDatabase bg = new LoadDatabase(this);
            bg.execute();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadingdatabase();
        //Load();
        setContentView(R.layout.activity_dashboard);
        getFormWidget();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_search){
            //code tại đây
            handleMenuSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void handleMenuSearch(){
        ActionBar action = getSupportActionBar(); //get the actionbar

        if(isSearchOpened){ //test if the search is open

            action.setDisplayShowCustomEnabled(false); //disable a custom view inside the actionbar
            action.setDisplayShowTitleEnabled(true); //show the title in the action bar

            //hides the keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edtSeach.getWindowToken(), 0);

            //add the search icon in the action bar
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_action));

            isSearchOpened = false;
        } else { //open the search entry

            action.setDisplayShowCustomEnabled(true); //enable it to display a
            // custom view in the action bar.
            action.setCustomView(R.layout.search_bar);//add the custom view
            action.setDisplayShowTitleEnabled(false); //hide the title

            edtSeach = (EditText)action.getCustomView().findViewById(R.id.edtSearch); //the text editor

            //this is a listener to do a search when the user clicks on search button
            edtSeach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        doSearch();
                        return true;
                    }
                    return false;
                }
            });

            edtSeach.requestFocus();

            //open the keyboard focused in the edtSearch
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edtSeach, InputMethodManager.SHOW_IMPLICIT);

            //add the close icon
            mSearchAction.setIcon(getResources().getDrawable(R.drawable.ic_close));

            isSearchOpened = true;
        }
    }
    private void doSearch() {

    }
    @Override
    public void onResume() {
        super.onResume();

    }

    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) //intent them tong tien
        {
            switch (resultCode) {
                case 1:
                    TongTien = data.getIntExtra("TongTien", 0);
                    divMoney(TongTien);
                    break;
                default: break;
            }

        }
    }

    public void divMoney(int tongtien) {
        DanhMucDAO dmtt = new DanhMucDAO(this);
        dmtt.update(1, tongtien);
        HuTienBL HuTiendbs = new HuTienBL(this);
        HuTiendbs.updateTienTungHu(0, true, tongtien*11/20); //NEC
        HuTiendbs.updateTienTungHu(1, true, tongtien/10); // LTSS
        HuTiendbs.updateTienTungHu(2, true, tongtien/10); // EDU
        HuTiendbs.updateTienTungHu(3, true, tongtien/10); // FFA
        HuTiendbs.updateTienTungHu(4, true, tongtien/10); // PLAY
        HuTiendbs.updateTienTungHu(5, true, tongtien/20); // GIVE
        onResume();
    }

    public void goGDTheoHu(int i) {
        Intent itn = new Intent(Dashboard.this, GiaoDichTrongHuTien.class);
        itn.putExtra("idHuTien", i);
        startActivity(itn);
    }

    public void showInfoHuTien(int position) {
        AlertDialog.Builder b = new AlertDialog.Builder(Dashboard.this);
        b.setTitle("Mô tả");
        b.setMessage(getData().get(position).information);
        b.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        b.create().show();
    }

    public List<HuTienDetail> getData(){
        List<HuTienDetail> data = new ArrayList<>();
        HuTienBL asd = new HuTienBL(this);
        List<HuTienItem> lst = asd.getAllHu();
        int size = lst.size();
        for (int i = 0; i < size; i++){
            HuTienDetail cur = new HuTienDetail(lst.get(i));
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

    public void Load() {
        DanhMucDAO dm = new DanhMucDAO(this);
        HuTienBL HuTiendb = new HuTienBL(this);
        if(dm.getMaxID() == -1) {
            dm.GenerateCode();
            HuTiendb.generate6HuTien(R.drawable.ic_nec, R.drawable.ic_ltss, R.drawable.ic_edu,
                    R.drawable.ic_ffa, R.drawable.ic_play, R.drawable.ic_give);
            TheLoaiBL dbTL = new TheLoaiBL(this);
            dbTL.generateTheLoai(R.drawable.ic_giadinhtl, R.drawable.ic_tinheutl, R.drawable.ic_hoctaptl,
                    R.drawable.ic_anuongtl, R.drawable.ic_gametl);
        }
        else
            TongTien = dm.getGiaTri(1);
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("Thông báo")
                    .setMessage("Bạn có muốn thoát ứng dụng?")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setPositiveButton("Có", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            l = false;
                            onStop();
                        }
                    });
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
    */

    //loaddatebase
    public class LoadDatabase extends AsyncTask<Void, Void, Void> {
        ProgressDialog dlLoad;
        Activity contextCha;

        public LoadDatabase(Activity ctx) {
            contextCha = ctx;
            dlLoad = new ProgressDialog(ctx);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlLoad.setMessage("Đang nạp dữ liệu...");
            dlLoad.setCanceledOnTouchOutside(false);
            dlLoad.show();
        }

        //tuyệt đối không được cập nhật giao diện trong hàm này
        @Override
        protected Void doInBackground(Void... arg0) {
            SystemClock.sleep(2000);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dlLoad.dismiss();
        }
    }

}