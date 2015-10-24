package com.duyhai.bloodkeeper;

import DTO.TheLoaiItem;

/**
 * Created by Duy on 6/24/2015.
 */
public class TheLoaiDetail {
    public int ID;
    public String TenTheLoai;
    public int icon;
    public int IDHuTien;

    public TheLoaiDetail(String TenTheLoai, int icon) {
        this.TenTheLoai = TenTheLoai;
        this.icon = icon;
    }

    public TheLoaiDetail(TheLoaiItem item) {
        this.ID = item.ID;
        this.TenTheLoai = item.name;
        this.icon = item.hinh;
        this.IDHuTien = item.IDHu;
    }
}
