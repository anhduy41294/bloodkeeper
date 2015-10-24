package DTO;

import android.support.v7.widget.Toolbar;

import com.duyhai.bloodkeeper.GiaoDichDetail;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by HaiTuan on 6/22/2015.
 */
public class GiaoDichItem {
    public int ID;
    public String TenGiaoDich;
    public long SoTien;
    public String GhiChu;
    public String NgayGD;
    public int KeHoach;
    public boolean Type;
    public int IDHu;
    public int IDTheLoai;
    public int HinhTheLoai;


    public GiaoDichItem(String name, long Money, String ghichu, String time, int Kehoach, boolean type, int idHu, int idTL, int TL)
    {
        this.TenGiaoDich = name;
        this.SoTien = Money;
        this.GhiChu = ghichu;
        this.NgayGD = time;
        this.KeHoach = Kehoach;
        this.Type = type;
        this.IDHu = idHu;
        this.IDTheLoai = idTL;
        this.HinhTheLoai = TL;
    }

    public GiaoDichItem(GiaoDichDetail gd) {
        this.TenGiaoDich = gd.TenGiaoDich;
        this.SoTien = gd.Money;
        this.GhiChu = gd.Note;
        this.NgayGD = gd.NgayGiaoDich;
        this.KeHoach = gd.IDKeHoach;
        this.Type = gd.Type;
        this.IDHu = gd.IDHuTien;
        this.IDTheLoai = gd.IDTheLoai;
        this.HinhTheLoai = gd.HinhTheLoai;
    }

    public GiaoDichItem() { }
}
