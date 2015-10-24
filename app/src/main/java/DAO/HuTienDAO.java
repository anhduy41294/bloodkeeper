package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import DTO.HuTienItem;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class HuTienDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_hutien = "HUTIEN";
    private static final String HT_ID = "ID";
    private static final String HT_Ten = "Ten";
    private static final String HT_Icon = "idIcon";
    private static final String HT_TongThu = "TienThu";
    private static final String HT_TongChi = "TienChi";
    private static final String HT_ThongTin = "ThongTin";

    public HuTienDAO(Context context) {
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
        String sql = "select MAX(ID) from " + TABLE_hutien;
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

    public boolean addHuTien(HuTienItem item) {
        connect();
        ContentValues cv = new ContentValues();
        cv.put(HT_ID, item.ID);
        cv.put(HT_Ten, item.tenHu);
        cv.put(HT_Icon, item.idIcon);
        cv.put(HT_TongThu, item.tongThu);
        cv.put(HT_TongChi, item.tongChi);
        cv.put(HT_ThongTin, item.thongtin);

        long index = db.insert(TABLE_hutien, null, cv);
        close();
        if (index == -1 ) {
            return false;
        }
        else
            return true;
    }

    public List<HuTienItem> getAllHuTien() {
        connect();
        String[] columns = new String[] {HT_ID, HT_Ten, HT_Icon, HT_TongThu, HT_TongChi, HT_ThongTin};
        Cursor c = db.query(TABLE_hutien, columns, null, null, null, null, null);

        int cID = c.getColumnIndex(HT_ID);
        int cTen = c.getColumnIndex(HT_Ten);
        int cIcon = c.getColumnIndex(HT_Icon);
        int cTongThu = c.getColumnIndex(HT_TongThu);
        int cTongChi = c.getColumnIndex(HT_TongChi);
        int cThongTin = c.getColumnIndex(HT_ThongTin);

        List<HuTienItem> lstItem = new ArrayList<HuTienItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(cID));
            String Ten = c.getString(cTen);
            int idIcon = Integer.parseInt(c.getString(cIcon));
            long thu = Long.parseLong(c.getString(cTongThu));
            long chi = Long.parseLong(c.getString(cTongChi));
            String thongtin = c.getString(cThongTin);

            HuTienItem item = new HuTienItem(Ten, idIcon, thu, chi, thongtin);
            item.ID = ID;
            lstItem.add(item);
        }
        c.close();
        close();
        return lstItem;
    }

    public boolean deleteHuTien(HuTienItem item) {
        connect();
        try {
            db.delete(TABLE_hutien, HT_ID + "=" + item.ID, null);
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
            db.delete(TABLE_hutien, null, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean updateHuTien(int ID, HuTienItem newItemUpdate) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            cv.put(HT_ID, newItemUpdate.ID);
            cv.put(HT_Ten, newItemUpdate.tenHu);
            cv.put(HT_Icon, newItemUpdate.idIcon);
            cv.put(HT_TongThu, newItemUpdate.tongThu);
            cv.put(HT_TongChi, newItemUpdate.tongChi);
            cv.put(HT_ThongTin, newItemUpdate.thongtin);

            db.update(TABLE_hutien, cv, HT_ID + "=?", new String[] { String.valueOf(ID)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }
}
