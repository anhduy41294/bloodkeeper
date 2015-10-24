package com.duyhai.bloodkeeper;

import DTO.KHSuKienItem;

/**
 * Created by Duy on 6/21/2015.
 */
public class KeHoachSuKienDetail {
    public int ID;
    public int iconID;
    public String title;
    public String information;
    public long tienthu;
    public long tienchi;
    public boolean apdung;

    public KeHoachSuKienDetail(int iconID, String title, String information, long tienchi, long tienthu, boolean apdung) {
        this.iconID = iconID;
        this.title = title;
        this.information = information;
        this.tienchi = tienchi;
        this.tienthu = tienthu;
        this.apdung = apdung;
    }

    public KeHoachSuKienDetail(KHSuKienItem sk) {
        this.ID = sk.ID;
        this.iconID = sk.iconID;
        this.title = sk.title;
        this.information = sk.information;
        this.tienchi = sk.tienchi;
        this.tienthu = sk.tienthu;
        this.apdung = sk.apdung;
    }
}
