package com.duyhai.bloodkeeper;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ThemTongTien extends ActionBarActivity {
    private Toolbar toolbar;
    EditText edtTongTien;
    Button btnOk;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_tong_tien);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle(R.string.title_activity_them_tong_tien);
        setSupportActionBar(toolbar);
        edtTongTien = (EditText)findViewById(R.id.edtTongTien);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doOk();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCancel();
            }
        });
    }

    public void doOk() {
        Intent back = getIntent();
        int tongtien = Integer.parseInt(edtTongTien.getText().toString());
        back.putExtra("TongTien", tongtien);
        setResult(1, back);
        finish();
    }

    public void doCancel() {
        Intent back = getIntent();
        back.putExtra("TongTien", 0);
        setResult(2, back);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_them_tong_tien, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
