package DTO;

import com.duyhai.bloodkeeper.TheLoaiDetail;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class TheLoaiItem {
    public int ID;
    public String name;
    public boolean type;
    public int hinh;
    public int IDHu;

    public TheLoaiItem(String name, boolean type, int image, int idHu) {
        this.name = name;
        this.type = type;
        this.hinh = image;
        this.IDHu = idHu;
    }

    public TheLoaiItem(String name, boolean type, int image) {
        this.name = name;
        this.type = type;
        this.hinh = image;
    }

    public TheLoaiItem() { }

    public TheLoaiItem(TheLoaiDetail dt){
        this.name = dt.TenTheLoai;
        this.hinh = dt.icon;
        this.type = true;
    }
}
