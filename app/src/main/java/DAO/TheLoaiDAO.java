package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import DTO.TheLoaiItem;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class TheLoaiDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_theloai = "THELOAI";
    private static final String TL_ID = "ID";
    private static final String TL_Name = "Ten";
    private static final String TL_Type = "Type";
    private static final String TL_BieuTuong = "Hinh";
    private static final String TL_IDHuTien = "HuTien";

    public TheLoaiDAO(Context context) {
        dl = new DatabaseLoad(context);
    }

    private void connect() {
        db= dl.getWritableDatabase();
    }

    private void close() {
        db.close();
    }

    public int getMaxID() {
        int IDmax = -1;
        connect();
        String sql = "select MAX(ID) from " + TABLE_theloai;
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

    public boolean addTheLoai(TheLoaiItem item) {
        try {
            connect();
            ContentValues cv = new ContentValues();
            cv.put(TL_ID, item.ID);
            cv.put(TL_Name, item.name);
            if(item.type) { cv.put(TL_Type, 1); }
            else { cv.put(TL_Type, 0); }
            cv.put(TL_BieuTuong, item.hinh);

            //Liên kết Thể Loại - Hủ Tiền
            cv.put(TL_IDHuTien, item.IDHu);

            long index = db.insert(TABLE_theloai, null, cv);
            close();
            if (index == -1 ) return false;
            else return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public List<TheLoaiItem> getAllTheLoai() {
        connect();
        String[] columns = new String[] {TL_ID, TL_Name, TL_Type, TL_BieuTuong, TL_IDHuTien};
        Cursor c = db.query(TABLE_theloai, columns, null, null, null, null, null);

        int cID = c.getColumnIndex(TL_ID);
        int cten = c.getColumnIndex(TL_Name);
        int cType = c.getColumnIndex(TL_Type);
        int cHinh = c.getColumnIndex(TL_BieuTuong);
        int cIdhutien = c.getColumnIndex(TL_IDHuTien);

        List<TheLoaiItem> lstItem = new ArrayList<TheLoaiItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(cID));
            String Ten = c.getString(cten);
            int Type = Integer.parseInt(c.getString(cType));
            boolean typeTL;
            if (Type == 0) { typeTL = false; }
            else { typeTL = true; }
            int Hinh = Integer.parseInt(c.getString(cHinh));
            int idHuTien = Integer.parseInt(c.getString(cIdhutien));

            //Có thêm IDHuTien
            TheLoaiItem item = new TheLoaiItem(Ten, typeTL, Hinh, idHuTien);
            item.ID = ID;

            lstItem.add(item);
        }
        c.close();
        close();
        return lstItem;
    }

    //Get Thể loại theo Hủ Tiền
    public List<TheLoaiItem> getTLTheoHT(int IDhutien) {
        connect();
        String[] columns = new String[] {TL_ID, TL_Name, TL_Type, TL_BieuTuong, TL_IDHuTien};
        Cursor c = db.query(TABLE_theloai, columns, TL_IDHuTien + "=?",
                new String[] { String.valueOf(IDhutien) }, null, null, null, null);


        List<TheLoaiItem> lstItem = new ArrayList<TheLoaiItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            TheLoaiItem tlItem = new TheLoaiItem();
            tlItem.ID = Integer.parseInt(c.getString(0));
            tlItem.name = c.getString(1);
            if ( Integer.parseInt(c.getString(2)) == 0 ) tlItem.type = false;
            else tlItem.type = true;
            tlItem.hinh = Integer.parseInt(c.getString(3));
            tlItem.IDHu = Integer.parseInt(c.getString(4));

            lstItem.add(tlItem);
        }
        c.close();
        close();
        return lstItem;
    }

    public boolean deleteTL(int idTL) {
        connect();
        try {
            db.delete(TABLE_theloai, TL_ID + "=" + idTL, null);
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
            db.delete(TABLE_theloai, null, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean updateTL(int idTL, TheLoaiItem newItemUpdate) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            cv.put(TL_ID, idTL);
            cv.put(TL_Name, newItemUpdate.name);
            if(newItemUpdate.type) { cv.put(TL_Type, 1); }
            else { cv.put(TL_Type, 0); }
            cv.put(TL_BieuTuong, newItemUpdate.hinh);

            //Thêm Hủ Tiền vào Thể Loại
            cv.put(TL_IDHuTien, newItemUpdate.IDHu);

            db.update(TABLE_theloai, cv, TL_ID + "=?", new String[] { String.valueOf(idTL)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }
}
