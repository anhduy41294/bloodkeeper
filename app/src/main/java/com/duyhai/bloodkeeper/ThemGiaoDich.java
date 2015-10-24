package com.duyhai.bloodkeeper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DTO.GiaoDichItem;
import bloodkeeperBus.GiaoDichBL;
import bloodkeeperBus.HuTienBL;
import bloodkeeperBus.KHSuKienBL;


public class ThemGiaoDich extends ActionBarActivity {

    private Toolbar toolbar;
    private EditText edtTenGiaoDich;
    private EditText edtSoTien;
    private EditText edtGhiChu;
    private EditText edtThoiGian;
    private EditText edtMucDichChi;
    private ImageView imgMucDichChi;
    private EditText edtKeHoach;
    private ImageView imgKeHoach;

    private int idHu;
    private int idGD;

    FloatingActionButton actionButton;
    private Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Load();
        setContentView(R.layout.activity_them_giao_dich);
        getFormWidget();
        addEvent();
    }

    public void getFormWidget() {
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.title_activity_them_giao_dich);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtTenGiaoDich = (EditText) findViewById(R.id.edtTenGiaoDich);
        edtSoTien = (EditText) findViewById(R.id.edtSoTien);
        edtGhiChu = (EditText) findViewById(R.id.edtGhiChu);
        edtThoiGian = (EditText) findViewById(R.id.edtNgayGiaoDich);
        edtMucDichChi = (EditText) findViewById(R.id.edtMucDichChi);
        imgMucDichChi = (ImageView) findViewById(R.id.imgMucDichChi);
        edtKeHoach = (EditText) findViewById(R.id.edtKeHoach);
        imgKeHoach = (ImageView) findViewById(R.id.imgKeHoach);
        edtThoiGian.setText(new Date().toLocaleString());

        LoadFloatingPoint();

        if(idGD == -1) {
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
            edtThoiGian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(ThemGiaoDich.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

        } else {
            GiaoDichBL up = new GiaoDichBL(this);
            GiaoDichItem curItem = up.getGiaoDich(idGD);
            toolbar.setTitle("Update");
            edtTenGiaoDich.setText(curItem.TenGiaoDich);
            edtSoTien.setText(String.valueOf(curItem.SoTien));
            edtGhiChu.setText(curItem.GhiChu);
            edtThoiGian.setText(curItem.NgayGD);
            edtMucDichChi.setText(String.valueOf(curItem.IDTheLoai));
            edtKeHoach.setText(String.valueOf(curItem.KeHoach));
        }
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
                addGiaoDich(idGD);
                finish();
            }
        });
    }

    public void addGiaoDich(int id) {
        try {
            String tenGD = edtTenGiaoDich.getText().toString();
            long tien = Long.parseLong(edtSoTien.getText().toString());
            String ghichu = edtGhiChu.getText().toString();
            String ngay = edtThoiGian.getText().toString();
            String ngaygd = ngay.split(" ")[0];
            GiaoDichDetail newItem = new GiaoDichDetail(tenGD, tien, false, ngaygd, ghichu, idHu, idIcon, idTL, idKH);
            GiaoDichBL db = new GiaoDichBL(this);
            if (id == -1) {
                if(db.addGiaoDich(newItem)) {
                    HuTienBL hu = new HuTienBL(this);
                    hu.updateTienTungHu(idHu, newItem.Type, newItem.Money);
                    if(newItem.IDKeHoach > -1) {
                        KHSuKienBL sk = new KHSuKienBL(this);
                        sk.themTien(newItem.ID, newItem.Type, newItem.Money);
                    }
                }
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
            }
            else {
                if(db.updateGD(id, new GiaoDichItem(newItem))) {
                    HuTienBL hu = new HuTienBL(this);
                    hu.updateTienTungHu(idHu, newItem.Type, newItem.Money);
                    if(newItem.IDKeHoach > -1) {
                        KHSuKienBL sk = new KHSuKienBL(this);
                        sk.themTien(newItem.ID, newItem.Type, newItem.Money);
                    }
                }
                Toast.makeText(this, "Update thành công", Toast.LENGTH_SHORT).show();
            }


        } catch (Exception ex) {
            Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtThoiGian.setText(sdf.format(myCalendar.getTime()));
    }

    private void addEvent() {

        edtMucDichChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ThemGiaoDich.this, TheLoaiActivity.class), 1);
            }
        });

        edtKeHoach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(ThemGiaoDich.this, ChonSuKien.class), 1);
            }
        });
    }

    int idTL = -1;
    int idKH = -1;
    int idIcon = -1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            switch (resultCode) {
                case 1:
                    edtMucDichChi.setText(data.getStringExtra("ten"));
                    idTL = data.getIntExtra("idTL", 0);
                    idIcon = data.getIntExtra("icon", R.drawable.ic_hutien);
                    break;
                case 2:
                    edtKeHoach.setText(data.getStringExtra("id"));
                    idKH = data.getIntExtra("idKH", -1);
                    break;
            }
        }
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
            ViewGroup group = (ViewGroup) findViewById(R.id.tblformGiaoDich);
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
        edtThoiGian.setText(new Date().toLocaleString());
    }

    public void Load() {
        Intent back = getIntent();
        idHu = back.getIntExtra("idHu", 0);
        idGD = back.getIntExtra("idGD", -1);
    }
}
