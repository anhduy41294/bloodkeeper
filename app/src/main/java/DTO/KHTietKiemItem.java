package DTO;

import com.duyhai.bloodkeeper.KeHoachTietKiemDetail;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class KHTietKiemItem {
    public int ID;
    public int iconID;
    public String title;
    public String information;
    public long tienmuctieu;
    public long tienhienco;
    public String ngaykethuc;

    public KHTietKiemItem(int iconID, String title, String information, long tienmuctieu, long tienhienco, String ngaykethuc) {
        this.iconID = iconID;
        this.title = title;
        this.information = information;
        this.tienmuctieu = tienmuctieu;
        this.tienhienco = tienhienco;
        this.ngaykethuc = ngaykethuc;
    }

    public KHTietKiemItem(KeHoachTietKiemDetail tk) {
        this.iconID = tk.iconID;
        this.title = tk.title;
        this.information = tk.information;
        this.tienmuctieu = tk.tienmuctieu;
        this.tienhienco = tk.tienhienco;
        this.ngaykethuc = tk.ngaykethuc;
    }

    public KHTietKiemItem() { }
}
