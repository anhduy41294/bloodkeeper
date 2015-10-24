package com.duyhai.bloodkeeper;

import java.util.Date;

import DTO.VayNoItem;

/**
 * Created by Duy on 6/21/2015.
 */
public class VayNoDetail {
    public int ID;
    public int iconID;
    public String title;
    public String information;
    public long tiendatra;
    public long tiencantra;
    public String ngaykethuc;


    public VayNoDetail(int iconID, String title, String information, long tiendatra, long tiencantra, String ngaykethuc) {
        this.iconID = iconID;
        this.title = title;
        this.information = information;
        this.tiendatra = tiendatra;
        this.tiencantra = tiencantra;
        this.ngaykethuc = ngaykethuc;
    }

    public VayNoDetail(VayNoItem vn) {
        this.ID = vn.ID;
        this.iconID = vn.idIcon;
        this.title = vn.ten;
        this.information = vn.ThongTin;
        this.tiendatra = vn.SoTienDaTra;
        this.tiencantra = vn.SoTienNo;
        this.ngaykethuc = vn.NgayTra;
    }
}
