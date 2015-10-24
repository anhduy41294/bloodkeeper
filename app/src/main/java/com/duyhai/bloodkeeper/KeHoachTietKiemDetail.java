package com.duyhai.bloodkeeper;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import DTO.KHTietKiemItem;

/**
 * Created by Duy on 6/21/2015.
 */
public class KeHoachTietKiemDetail {
    public int ID;
    public int iconID;
    public String title;
    public String information;
    public long tienmuctieu;
    public long tienhienco;
    public String ngaykethuc;


    public KeHoachTietKiemDetail(int iconID, String title, String information, long tienmuctieu, long tienhienco, String ngaykethuc) {
        this.iconID = iconID;
        this.title = title;
        this.information = information;
        this.tienmuctieu = tienmuctieu;
        this.tienhienco = tienhienco;
        this.ngaykethuc = ngaykethuc;

    }

    public KeHoachTietKiemDetail(KHTietKiemItem kh) {
        this.ID = kh.ID;
        this.iconID = kh.iconID;
        this.title = kh.title;
        this.information = kh.information;
        this.tienmuctieu = kh.tienmuctieu;
        this.tienhienco = kh.tienhienco;
        this.ngaykethuc = kh.ngaykethuc;
    }

}
