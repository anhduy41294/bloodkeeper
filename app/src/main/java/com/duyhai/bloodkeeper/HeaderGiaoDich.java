package com.duyhai.bloodkeeper;

/**
 * Created by Duy on 6/24/2015.
 */
public class HeaderGiaoDich implements IGiaoDichItem {
    public String content;
    public long tien;
    public boolean trangthai;
    @Override
    public int getViewType() {
        return 0;
    }

    public HeaderGiaoDich(String content, long tien, boolean trangthai){
        this.content = content;
        this.tien = tien;
        this.trangthai = trangthai;
    }
}
