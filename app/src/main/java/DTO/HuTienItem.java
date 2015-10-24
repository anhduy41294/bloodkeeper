package DTO;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class HuTienItem {
    public int ID;
    public String tenHu;
    public int idIcon;
    public long tongThu;
    public long tongChi;
    public String thongtin;

    public HuTienItem(String name, int img, long TongThu, long TongChi, String info)
    {
        this.tenHu = name;
        this.idIcon = img;
        this.tongThu = TongThu;
        this.tongChi = TongChi;
        this.thongtin = info;
    }

    public HuTienItem() { }
}
