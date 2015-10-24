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
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bloodkeeperBus.KHTietKiemBL;


public class ThemTietKiem extends ActionBarActivity {

    private Toolbar toolbar;
    private EditText edtTenTietKiem;
    private EditText edtSoTienDaCo;
    private EditText edtSoTienCanDat;
    private EditText edtGhiChu;
    private EditText edtNgayHetHan;

    FloatingActionButton actionButton;
    private Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tiet_kiem);
        getFormWidget();
        addEvent();
    }



    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.title_activity_them_tiet_kiem);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        edtTenTietKiem = (EditText) findViewById(R.id.edtTenTietKiem);
        edtSoTienDaCo = (EditText) findViewById(R.id.edtSoTienDaCo);
        edtSoTienCanDat = (EditText) findViewById(R.id.edtSoTienCanDat);
        edtNgayHetHan = (EditText) findViewById(R.id.edtNgayHetHanTK);
        edtGhiChu = (EditText) findViewById(R.id.edtThongTinTietKiem);
        edtNgayHetHan.setText(new Date().toLocaleString());

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        edtNgayHetHan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ThemTietKiem.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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
                //Save Action
                addTietKiem();
                //startActivity(new Intent(ThemTietKiem.this, KeHoachActivity.class));
                finish();
            }
        });
    }

    public void addTietKiem() {
        try {
            String ten = edtTenTietKiem.getText().toString();
            long muctieu = Long.parseLong(edtSoTienCanDat.getText().toString());
            long hienco = Long.parseLong(edtSoTienDaCo.getText().toString());
            String info = edtGhiChu.getText().toString();
            String date = edtNgayHetHan.getText().toString().split(" ")[0];

            KeHoachTietKiemDetail item = new KeHoachTietKiemDetail(R.drawable.ic_hutien, ten, info, muctieu, hienco, date);
            KHTietKiemBL db = new KHTietKiemBL(this);
            db.themSK(item);

            Intent sv1 = new Intent(getBaseContext(), TietKiemService.class);
            sv1.putExtra("id", item.ID);
            startService(sv1);

            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtNgayHetHan.setText(sdf.format(myCalendar.getTime()));
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
            ViewGroup group = (ViewGroup) findViewById(R.id.tblformTietKiem);
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
        edtNgayHetHan.setText(new Date().toLocaleString());
    }
}
