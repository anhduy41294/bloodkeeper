package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import DTO.KHSuKienItem;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class KHSuKienDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_KHSuKien = "KHSUKIEN";
    private static final String KHSK_ID = "ID";
    private static final String KHSK_Ten = "Ten";
    private static final String KHSK_TienThu = "TienThu";
    private static final String KHSK_TienChi = "TienChi";
    private static final String KHSK_iconID = "idIcon";
    private static final String KHSK_ThongTin = "ThongTin";
    private static final String KHSK_ApDung = "ApDung";

    public KHSuKienDAO(Context context) {
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
        String sql = "select MAX(ID) from " + TABLE_KHSuKien;
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

    public boolean addKHSuKien(KHSuKienItem item) {
        connect();
        ContentValues cv = new ContentValues();
        cv.put(KHSK_ID, item.ID);
        cv.put(KHSK_Ten, item.title);
        cv.put(KHSK_TienThu, item.tienthu);
        cv.put(KHSK_TienChi, item.tienchi);
        cv.put(KHSK_iconID, item.iconID);
        cv.put(KHSK_ThongTin, item.information);
        if(item.apdung)   cv.put(KHSK_ApDung, 1);
        else    cv.put(KHSK_ApDung, 0);

        long index = db.insert(TABLE_KHSuKien, null, cv);
        close();
        if (index == -1 ) { return false; }
        else return true;
    }

    public List<KHSuKienItem> getAllKHSK() {
        connect();
        String[] columns = new String[] {KHSK_ID, KHSK_Ten, KHSK_TienThu, KHSK_TienChi, KHSK_iconID, KHSK_ThongTin, KHSK_ApDung};
        Cursor c = db.query(TABLE_KHSuKien, columns, null, null, null, null, null);

        int cID = c.getColumnIndex(KHSK_ID);
        int cten = c.getColumnIndex(KHSK_Ten);
        int ctienthu = c.getColumnIndex(KHSK_TienThu);
        int ctienchi = c.getColumnIndex(KHSK_TienChi);
        int cIcon = c.getColumnIndex(KHSK_iconID);
        int cthongtin = c.getColumnIndex(KHSK_ThongTin);
        int cApdung = c.getColumnIndex(KHSK_ApDung);

        List<KHSuKienItem> lstItem = new ArrayList<KHSuKienItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(cID));
            String Ten = c.getString(cten);
            long Thu = Long.parseLong(c.getString(ctienthu));
            long Chi = Long.parseLong(c.getString(ctienchi));
            int Icon = Integer.parseInt(c.getString(cIcon));
            String ThongTin = c.getString(cthongtin);
            int bApDung = Integer.parseInt(c.getString(cApdung));
            boolean ApDung;
            if (bApDung == 0) ApDung = false;
            else ApDung = true;

            KHSuKienItem item = new KHSuKienItem(Icon, Ten, ThongTin, Chi, Thu, ApDung);
            item.ID = ID;
            lstItem.add(item);
        }
        c.close();
        close();
        return lstItem;
    }


    public KHSuKienItem getKHSK(int idSK) {
        connect();
        String[] columns = new String[] {KHSK_ID, KHSK_Ten, KHSK_TienThu, KHSK_TienChi, KHSK_iconID, KHSK_ThongTin, KHSK_ApDung};
        Cursor c = db.query(TABLE_KHSuKien, columns, KHSK_ID + "=?",
                new String[] { String.valueOf(idSK) }, null, null, null, null);
        if (c != null)
            c.moveToFirst();

        KHSuKienItem rs = new KHSuKienItem();
        rs.ID = Integer.parseInt(c.getString(0));
        rs.title = c.getString(1);
        rs.tienthu = Long.parseLong(c.getString(2));
        rs.tienchi = Long.parseLong(c.getString(3));
        rs.iconID = Integer.parseInt(c.getString(4));
        rs.information = c.getString(5);
        if ( Integer.parseInt(c.getString(6)) == 0 )  rs.apdung = false;
        else  rs.apdung = true;

        close();
        return rs;
    }

    public boolean deleteKHSK(int idSK) {
        connect();
        try {
            db.delete(TABLE_KHSuKien, KHSK_ID + "=" + idSK, null);
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
            db.delete(TABLE_KHSuKien, null, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean updateKHSK(int ID, KHSuKienItem newItemUpdate) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KHSK_ID, ID);
            cv.put(KHSK_Ten, newItemUpdate.title);
            cv.put(KHSK_TienThu, newItemUpdate.tienthu);
            cv.put(KHSK_TienChi, newItemUpdate.tienchi);
            cv.put(KHSK_iconID, newItemUpdate.iconID);
            cv.put(KHSK_ThongTin, newItemUpdate.information);
            if(newItemUpdate.apdung)   cv.put(KHSK_ApDung, 1);
            else    cv.put(KHSK_ApDung, 0);

            db.update(TABLE_KHSuKien, cv, KHSK_ID + "=?", new String[] { String.valueOf(ID)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }
}
