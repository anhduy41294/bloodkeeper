package DTO;

import com.duyhai.bloodkeeper.KeHoachSuKienDetail;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class KHSuKienItem {
    public int ID;
    public int iconID;
    public String title;
    public String information;
    public long tienthu;
    public long tienchi;
    public boolean apdung;

    public KHSuKienItem(int iconID, String title, String information, long tienchi, long tienthu, boolean apdung) {
        this.iconID = iconID;
        this.title = title;
        this.information = information;
        this.tienchi = tienchi;
        this.tienthu = tienthu;
        this.apdung = apdung;
    }

    public KHSuKienItem(KeHoachSuKienDetail sk) {
        this.iconID = sk.iconID;
        this.title = sk.title;
        this.information = sk.information;
        this.tienchi = sk.tienchi;
        this.tienthu = sk.tienthu;
        this.apdung = sk.apdung;
    }

    public KHSuKienItem() { }
}
