package DTO;

import com.duyhai.bloodkeeper.VayNoDetail;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class VayNoItem {
    public int ID;
    public String ten;
    public long SoTienNo;
    public long SoTienDaTra;
    public int idIcon;
    public String ThongTin;
    public boolean Type; //true= chovay; false= muon no
    public String NgayNo;
    public String NgayTra;

    public VayNoItem(String name, long tienNo, long tiendatra, int icon, String info, boolean loai, String ngayno, String ngaytra) {
        this.ten = name;
        this.SoTienNo = tienNo;
        this.SoTienDaTra = tiendatra;
        this.idIcon = icon;
        this.ThongTin = info;
        this.Type = loai;
        this.NgayNo = ngayno;
        this.NgayTra = ngaytra;
    }

    public VayNoItem(VayNoDetail vayno, boolean loai, String ngayno) {
        this.ten = vayno.title;
        this.SoTienNo = vayno.tiencantra;
        this.SoTienDaTra = vayno.tiendatra;
        this.idIcon = vayno.iconID;
        this.ThongTin = vayno.information;
        this.NgayTra = vayno.ngaykethuc;
        this.Type = loai;
        this.NgayNo = ngayno;
    }

    public VayNoItem() { }
}
