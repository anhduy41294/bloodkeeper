package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import DTO.GiaoDichItem;
import DTO.TheLoaiItem;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class GiaoDichDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_giaodich = "GIAODICH";
    private static final String GD_ID = "ID"; //int
    private static final String GD_Ten = "TenGiaoDich"; //string
    private static final String GD_Tien = "SoTien";  //long
    private static final String GD_NgayGiaoDich = "NgayGiaoDich"; //date
    private static final String GD_Type = "Loai"; // bool
    private static final String GD_GhiChu = "GhiChu"; //string
    private static final String GD_IDhutien = "IDHuTien"; //int
    private static final String GD_IDTheLoai = "TheLoai"; // int
    private static final String GD_HinhTheLoai = "HinhTheLoai"; //int
    private static final String GD_IDKeHoach = "IDKeHoach"; //int

    public GiaoDichDAO(Context context) {
        dl = new DatabaseLoad(context);
    }

    private void connect() {
        db = dl.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public int getMaxID() {
        int IDmax = -1;
        connect();
        String sql = "select MAX(ID) from " + TABLE_giaodich;
        Cursor cs = db.rawQuery(sql, null);
        try {
            if (cs != null) {
                cs.moveToFirst();
                IDmax = Integer.parseInt(cs.getString(0));
            }
        } catch (Exception ex) {}
        cs.close();
        close();
        return IDmax;
    }

    public boolean addGiaoDich(GiaoDichItem item) {
        try {
            connect();
            ContentValues cv = new ContentValues();
            cv.put(GD_ID, item.ID);
            cv.put(GD_Ten, item.TenGiaoDich);
            cv.put(GD_Tien, item.SoTien);
            cv.put(GD_NgayGiaoDich, item.NgayGD);
            if(item.Type)   cv.put(GD_Type, 1);
            else    cv.put(GD_Type, 0);
            cv.put(GD_GhiChu, item.GhiChu);
            cv.put(GD_IDhutien, item.IDHu);
            cv.put(GD_IDTheLoai, item.IDTheLoai);
            cv.put(GD_HinhTheLoai, item.HinhTheLoai);
            cv.put(GD_IDKeHoach, item.KeHoach);

            long index = db.insert(TABLE_giaodich, null, cv);
            close();
            if (index == -1 ) { return false; }
            else { return true; }
        }
        catch (Exception ex) {
            return false;
        }
    }

    public GiaoDichItem getGD(int id) {
        connect();
        String[] columns = new String[] {GD_ID, GD_Ten, GD_Tien, GD_NgayGiaoDich, GD_Type, GD_GhiChu, GD_IDhutien, GD_IDTheLoai, GD_HinhTheLoai, GD_IDKeHoach};
        Cursor c = db.query(TABLE_giaodich, columns, GD_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        GiaoDichItem gdItem = new GiaoDichItem();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            gdItem.ID = Integer.parseInt(c.getString(0));
            gdItem.TenGiaoDich = c.getString(1);
            gdItem.SoTien = Long.parseLong(c.getString(2));
            gdItem.NgayGD = c.getString(3);
            if ( Integer.parseInt(c.getString(4)) == 0 ) gdItem.Type = false;
            else gdItem.Type = true;
            gdItem.GhiChu = c.getString(5);
            gdItem.IDHu = Integer.parseInt(c.getString(6));
            gdItem.IDTheLoai = Integer.parseInt(c.getString(7));
            gdItem.HinhTheLoai = Integer.parseInt(c.getString(8));
            gdItem.KeHoach = Integer.parseInt(c.getString(9));
        }
        c.close();
        close();
        // return contact
        return gdItem;
    }

    public List<GiaoDichItem> getAllGiaoDich() {
        connect();
        String[] columns = new String[] {GD_ID, GD_Ten, GD_Tien, GD_NgayGiaoDich, GD_Type, GD_GhiChu, GD_IDhutien,GD_IDTheLoai, GD_HinhTheLoai, GD_IDKeHoach};
        Cursor c = db.query(TABLE_giaodich, columns, null, null, null, null, null);

        int cID = c.getColumnIndex(GD_ID);
        int cten = c.getColumnIndex(GD_Ten);
        int ctien = c.getColumnIndex(GD_Tien);
        int cNgayGiaoDich = c.getColumnIndex(GD_NgayGiaoDich);
        int cType = c.getColumnIndex(GD_Type);
        int cGhiChu = c.getColumnIndex(GD_GhiChu);
        int cIdhutien = c.getColumnIndex(GD_IDhutien);
        int cIdTheLoai = c.getColumnIndex(GD_IDTheLoai);
        int cHinhTheLoai = c.getColumnIndex(GD_HinhTheLoai);
        int cIdKehoach = c.getColumnIndex(GD_IDKeHoach);

        List<GiaoDichItem> lstItem = new ArrayList<GiaoDichItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(cID));
            String Ten = c.getString(cten);
            long Tien = Long.parseLong(c.getString(ctien));
            String NgayGD =  c.getString(cNgayGiaoDich);
            int Type = Integer.parseInt(c.getString(cType));
            boolean typeGD;
            if (Type == 0)  typeGD = false;
            else typeGD = true;
            String GhiChu = c.getString(cGhiChu);
            int idHuTien = Integer.parseInt(c.getString(cIdhutien));
            int idTheLoai = Integer.parseInt(c.getString(cIdTheLoai));
            int idHinhTheLoai = Integer.parseInt(c.getString(cHinhTheLoai));
            int idKehoach = Integer.parseInt(c.getString(cIdKehoach));

            GiaoDichItem item = new GiaoDichItem(Ten, Tien, GhiChu, NgayGD, idKehoach, typeGD, idHuTien, idTheLoai, idHinhTheLoai);
            item.ID = ID;

            lstItem.add(item);
        }
        c.close();
        close();
        return lstItem;
    }

    public List<GiaoDichItem> getGDTheoHu(int IDhutien) {
        connect();
        String[] columns = new String[] {GD_ID, GD_Ten, GD_Tien, GD_NgayGiaoDich, GD_Type, GD_GhiChu, GD_IDhutien, GD_IDTheLoai, GD_HinhTheLoai, GD_IDKeHoach};
        Cursor c = db.query(TABLE_giaodich, columns, GD_IDhutien + "=?",
                new String[] { String.valueOf(IDhutien) }, null, null, null, null);

        List<GiaoDichItem> lstGD = new ArrayList<GiaoDichItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            GiaoDichItem gdItem = new GiaoDichItem();
            gdItem.ID = Integer.parseInt(c.getString(0));
            gdItem.TenGiaoDich = c.getString(1);
            gdItem.SoTien = Long.parseLong(c.getString(2));
            gdItem.NgayGD= c.getString(3);
            if ( Integer.parseInt(c.getString(4)) == 0 ) gdItem.Type = false;
            else gdItem.Type = true;
            gdItem.GhiChu = c.getString(5);
            gdItem.IDTheLoai = Integer.parseInt(c.getString(7));
            gdItem.HinhTheLoai = Integer.parseInt(c.getString(8));
            gdItem.KeHoach = Integer.parseInt(c.getString(9));
            gdItem.IDHu  = IDhutien;

            lstGD.add(gdItem);
        }
        c.close();
        close();
        // return contact
        return lstGD;
    }

    public List<GiaoDichItem> getGDTheoTL(int idTheLoai) {
        connect();
        String[] columns = new String[] {GD_ID, GD_Ten, GD_Tien, GD_NgayGiaoDich, GD_Type, GD_GhiChu, GD_IDhutien, GD_IDTheLoai, GD_HinhTheLoai, GD_IDKeHoach};
        Cursor c = db.query(TABLE_giaodich, columns, GD_IDTheLoai + "=?",
                new String[] { String.valueOf(idTheLoai) }, null, null, null, null);

        List<GiaoDichItem> lstGD = new ArrayList<GiaoDichItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            GiaoDichItem gdItem = new GiaoDichItem();
            gdItem.ID = Integer.parseInt(c.getString(0));
            gdItem.TenGiaoDich = c.getString(1);
            gdItem.SoTien = Long.parseLong(c.getString(2));
            gdItem.NgayGD = c.getString(3);
            if ( Integer.parseInt(c.getString(4)) == 0 ) gdItem.Type = false;
            else gdItem.Type = true;
            gdItem.GhiChu = c.getString(5);
            gdItem.IDHu = Integer.parseInt(c.getString(6));

            gdItem.HinhTheLoai = Integer.parseInt(c.getString(8));
            gdItem.KeHoach = Integer.parseInt(c.getString(9));
            gdItem.IDTheLoai = idTheLoai;

            lstGD.add(gdItem);
        }
        c.close();
        close();
        // return contact
        return lstGD;
    }

    public List<GiaoDichItem> getGDTheoKH(int idKeHoach) {
        connect();
        String[] columns = new String[] {GD_ID, GD_Ten, GD_Tien, GD_NgayGiaoDich, GD_Type, GD_GhiChu, GD_IDhutien, GD_IDTheLoai, GD_HinhTheLoai, GD_IDKeHoach};
        Cursor c = db.query(TABLE_giaodich, columns, GD_IDKeHoach + "=?",
                new String[] { String.valueOf(idKeHoach) }, null, null, null, null);

        List<GiaoDichItem> lstGD = new ArrayList<GiaoDichItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            GiaoDichItem gdItem = new GiaoDichItem();
            gdItem.ID = Integer.parseInt(c.getString(0));
            gdItem.TenGiaoDich = c.getString(1);
            gdItem.SoTien = Long.parseLong(c.getString(2));
            gdItem.NgayGD = c.getString(3);
            if ( Integer.parseInt(c.getString(4)) == 0 ) gdItem.Type = false;
            else gdItem.Type = true;
            gdItem.GhiChu = c.getString(5);
            gdItem.IDHu = Integer.parseInt(c.getString(6));
            gdItem.IDTheLoai = Integer.parseInt(c.getString(7));
            gdItem.HinhTheLoai = Integer.parseInt(c.getString(8));
            gdItem.KeHoach = idKeHoach;

            lstGD.add(gdItem);
        }
        c.close();
        close();
        // return contact
        return lstGD;
    }

    public boolean deleteGD(int idGD) {
        connect();
        try {
            db.delete(TABLE_giaodich, GD_ID + "=" + idGD, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean deleteAll() {
        connect();
        try {
            db.delete(TABLE_giaodich, null, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean updateGD(int idGD, GiaoDichItem newItemUpdate) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            cv.put(GD_Ten, newItemUpdate.TenGiaoDich);
            cv.put(GD_Tien, newItemUpdate.SoTien);
            cv.put(GD_NgayGiaoDich, newItemUpdate.NgayGD);
            if(newItemUpdate.Type) { cv.put(GD_Type, 1); }
            else { cv.put(GD_Type, 0); }
            cv.put(GD_GhiChu, newItemUpdate.GhiChu);
            cv.put(GD_IDhutien, newItemUpdate.IDHu);
            cv.put(GD_IDTheLoai, newItemUpdate.IDTheLoai);
            cv.put(GD_HinhTheLoai, newItemUpdate.HinhTheLoai);
            cv.put(GD_IDKeHoach, newItemUpdate.KeHoach);


            db.update(TABLE_giaodich, cv, GD_ID + "=?", new String[] { String.valueOf(idGD)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }
}
