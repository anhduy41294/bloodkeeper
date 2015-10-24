package com.duyhai.bloodkeeper;

import DTO.HuTienItem;

/**
 * Created by Duy on 5/24/2015.
 */
public class HuTienDetail {
    public int ID;
    public int iconID;
    public String title;
    public String information;
    public long tienthu;
    public long tienchi;

    public HuTienDetail(int iconID, String title, String information, long tienchi, long tienthu){
        this.iconID = iconID;
        this.title = title;
        this.information = information;
        this.tienchi = tienchi;
        this.tienthu = tienthu;
    }

    public HuTienDetail(HuTienItem ht){
        this.ID = ht.ID;
        this.iconID = ht.idIcon;
        this.title = ht.tenHu;
        this.information = ht.thongtin;
        this.tienchi = ht.tongChi;
        this.tienthu = ht.tongThu;
    }
}
