package com.duyhai.bloodkeeper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.duyhai.bloodkeeper.R;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import DAO.TheLoaiDAO;
import DTO.GiaoDichItem;
import DTO.TheLoaiItem;
import bloodkeeperBus.GiaoDichBL;
import bloodkeeperBus.TheLoaiBL;

public class GiaoDichTrongHuTien extends ActionBarActivity {

    private Toolbar toolbar;
    private GiaoDichAdapter adapter;
    private RecyclerView recyclerView;

    private MenuItem mSearchAction;
    private boolean isSearchOpened = false;
    private EditText edtSeach;
    private static int idHu = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Load();

        setContentView(R.layout.activity_giao_dich_trong_hu_tien);
        getFormWidget();
    }

    public String getTenHu(){
        switch (idHu) {
            case 0: return "NEC";
            case 1: return "LTSS";
            case 2: return "EDU";
            case 3: return "FFA";
            case 4: return "PLAY";
            case 5: return "GIVE";
            default: return "NEC";
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        mSearchAction = menu.findItem(R.id.action_search);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_giao_dich_trong_hu_tien, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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

    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(getTenHu());

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.drawerListGiaoDich);
        adapter = new GiaoDichAdapter(GiaoDichTrongHuTien.this,getData());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(GiaoDichTrongHuTien.this));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(GiaoDichTrongHuTien.this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View v, int position) {
                try {
                    GiaoDichDetail itemSelected = (GiaoDichDetail)getData().get(position);
                    showDialog(itemSelected);
                } catch (Exception ex) {}
            }

            @Override
            public void onLongClick(View v, int position) {
            }
        }));

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_add_blue);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent themgd = new Intent(GiaoDichTrongHuTien.this, ThemGiaoDich.class);
                themgd.putExtra("idHu", idHu);
                startActivity(themgd);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new GiaoDichAdapter(GiaoDichTrongHuTien.this,getData());

        recyclerView.setAdapter(adapter);
    }

    public void showDialog(final GiaoDichDetail item) {
        AlertDialog.Builder b = new AlertDialog.Builder(GiaoDichTrongHuTien.this);
        b.setTitle("Chọn chức năng");
        b.setNeutralButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent themgd = new Intent(GiaoDichTrongHuTien.this, ThemGiaoDich.class);
                themgd.putExtra("idGD", item.ID);
                startActivity(themgd);
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
                GiaoDichBL db = new GiaoDichBL(getBaseContext());
                db.delete(item.ID);
                onResume();
            }
        });
        b.create().show();
    }

    public List<IGiaoDichItem> getData(){
        GiaoDichBL getGD = new GiaoDichBL(this);
        List<GiaoDichItem> lst = getGD.getTheoHu(idHu, 0);
        int size = lst.size();
        List<IGiaoDichItem> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            GiaoDichItem item = lst.get(i);
            HeaderGiaoDich header = new HeaderGiaoDich(item.NgayGD, item.SoTien, item.Type);
            TheLoaiBL TheLoaidb = new TheLoaiBL(this);
            List<TheLoaiItem> lsttl = TheLoaidb.getAll();
            int sizetl = lsttl.size();
            String tentl = "";
            for(int j = 0; j < sizetl; ++j)
                if(lsttl.get(j).ID == item.IDTheLoai) {
                    tentl = lsttl.get(j).name;
                    break;
                }
            GiaoDichDetail gd = new GiaoDichDetail(item);
            data.add(header);
            data.add(gd);
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
        Intent i = getIntent();
        idHu = i.getIntExtra("idHuTien", 0);
    }

}
