package com.duyhai.bloodkeeper;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

import DTO.GiaoDichItem;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Duy on 6/21/2015.
 */
public class GiaoDichDetail implements IGiaoDichItem {
    public int ID;
    public String TenGiaoDich;//
    public long Money;//
    public boolean Type; //
    public String NgayGiaoDich;//
    public String Note; //
    public int IDHuTien; //
    public int HinhTheLoai;
    public int IDTheLoai; //
    public String TenTheLoai;
    public boolean LapLai = false;
    public int IDKeHoach; //
    public Time ThoiGianLapLai;

    public GiaoDichDetail(String TenGiaoDich, long Money,boolean Type, String NgayGiaoDich, String Note, int IDHuTien, int HinhTheLoai, int IDTheLoai, int IDKeHoach )
    {
        this.TenGiaoDich = TenGiaoDich;
        this.Money = Money;
        this.Type = Type;
        this.NgayGiaoDich = NgayGiaoDich;
        this.Note = Note;
        this.IDHuTien = IDHuTien;
        this.HinhTheLoai = HinhTheLoai;
        this.IDTheLoai = IDTheLoai;
        this.IDKeHoach = IDKeHoach;
    }

    public GiaoDichDetail(GiaoDichItem gd)
    {
        this.ID = gd.ID;
        this.TenGiaoDich = gd.TenGiaoDich;
        this.Money = gd.SoTien;
        this.Type = gd.Type;
        this.NgayGiaoDich = gd.NgayGD;
        this.Note = gd.GhiChu;
        this.IDHuTien = gd.IDHu;
        this.HinhTheLoai = gd.HinhTheLoai;
        this.IDTheLoai = gd.IDTheLoai;
        this.IDKeHoach = gd.KeHoach;
    }

    @Override
    public int getViewType() {
        return 1;
    }

}
