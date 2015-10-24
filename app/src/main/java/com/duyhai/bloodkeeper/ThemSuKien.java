package com.duyhai.bloodkeeper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bloodkeeperBus.KHSuKienBL;


public class ThemSuKien extends ActionBarActivity {

    private Toolbar toolbar;
    private EditText edtTenSuKien;
    private EditText edtThongTinSuKien;
    FloatingActionButton actionButton;
    private Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_su_kien);
        getFormWidget();
        addEvent();
    }



    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.title_activity_them_su_kien);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtTenSuKien = (EditText) findViewById(R.id.edtTenSuKien);
        edtThongTinSuKien =(EditText) findViewById(R.id.edtThongTinSuKien);

        LoadFloatingPoint();
    }

    private void LoadFloatingPoint() {
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.drawable.ic_save_blue);
        actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        /*FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        actionButton.setPosition(FloatingActionButton.POSITION_BOTTOM_CENTER, tvParams);*/

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSuKien();
                //startActivity(new Intent(ThemSuKien.this, KeHoachActivity.class));
                finish();
            }
        });
    }

    public void addSuKien() {
        try {
            String ten = edtTenSuKien.getText().toString();
            String ghichu = edtThongTinSuKien.getText().toString();

            KeHoachSuKienDetail item = new KeHoachSuKienDetail(R.drawable.ic_kehoach, ten, ghichu, 0, 0, true);
            KHSuKienBL db = new KHSuKienBL(this);
            db.themSK(item);
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }

    }

    private void addEvent() {

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
            clearForm(group);
        }

        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearForm(ViewGroup group)
    {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
        //edtNgayHetHan.setText(new Date().toLocaleString());
    }
}
