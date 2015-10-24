package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import DTO.KHTietKiemItem;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class KHTietKiemDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_KHTietKiem = "KHTIETKIEM";
    private static final String KHTK_ID = "ID";
    private static final String KHTK_Ten = "Ten";
    private static final String KHTK_iconID = "idIcon";
    private static final String KHTK_ThongTin = "ThongTin";
    private static final String KHTK_TienMucTieu = "TienMucTieu";
    private static final String KHTK_TienDaTK = "TienDaTK";
    private static final String KHTK_NgayKetThuc = "NgayKetThuc";

    public KHTietKiemDAO(Context context) {
        dl = new DatabaseLoad(context);
    }

    public void connect() {
        db= dl.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public int getMaxID() {
        int IDmax = -1;
        connect();
        String sql = "select MAX(ID) from " + TABLE_KHTietKiem;
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

    public boolean addKHTietKiem(KHTietKiemItem item) {
        connect();
        ContentValues cv = new ContentValues();
        cv.put(KHTK_ID, item.ID);
        cv.put(KHTK_Ten, item.title);
        cv.put(KHTK_iconID, item.iconID);
        cv.put(KHTK_ThongTin, item.information);
        cv.put(KHTK_TienMucTieu, item.tienmuctieu);
        cv.put(KHTK_TienDaTK, item.tienhienco);
        cv.put(KHTK_NgayKetThuc, item.ngaykethuc);

        long index = db.insert(TABLE_KHTietKiem, null, cv);
        close();
        if (index == -1 ) { return false; }
        else return true;
    }

    public List<KHTietKiemItem> getAllKHTK() {
        connect();
        String[] columns = new String[] { KHTK_ID, KHTK_Ten, KHTK_iconID, KHTK_ThongTin, KHTK_TienMucTieu, KHTK_TienDaTK, KHTK_NgayKetThuc };
        Cursor c = db.query(TABLE_KHTietKiem, columns, null, null, null, null, null);

        int cID = c.getColumnIndex(KHTK_ID);
        int cten = c.getColumnIndex(KHTK_Ten);
        int cIcon = c.getColumnIndex(KHTK_iconID);
        int cthongtin = c.getColumnIndex(KHTK_ThongTin);
        int ctienmt = c.getColumnIndex(KHTK_TienMucTieu);
        int ctienTK = c.getColumnIndex(KHTK_TienDaTK);
        int cngay = c.getColumnIndex(KHTK_NgayKetThuc);

        List<KHTietKiemItem> lstItem = new ArrayList<KHTietKiemItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(cID));
            String Ten = c.getString(cten);
            int idIcon = Integer.parseInt(c.getString(cIcon));
            String thongtin = c.getString(cthongtin);
            long TienTK = Integer.parseInt(c.getString(ctienmt));
            long TienDaTK = Integer.parseInt(c.getString(ctienTK));
            String ngayKT = c.getString(cngay);

            KHTietKiemItem item = new KHTietKiemItem(idIcon, Ten, thongtin, TienTK, TienDaTK, ngayKT);
            item.ID = ID;
            lstItem.add(item);
        }
        c.close();
        close();
        return lstItem;
    }

    public KHTietKiemItem getKHTK(int idTK) {
        connect();
        String[] columns = new String[] {KHTK_ID, KHTK_Ten, KHTK_iconID, KHTK_ThongTin, KHTK_TienMucTieu, KHTK_TienDaTK, KHTK_NgayKetThuc};
        Cursor c = db.query(TABLE_KHTietKiem, columns, KHTK_ID + "=?",
                new String[] { String.valueOf(idTK) }, null, null, null, null);
        if (c != null)
            c.moveToFirst();

        KHTietKiemItem rs = new KHTietKiemItem();
        rs.ID = Integer.parseInt(c.getString(0));
        rs.title = c.getString(1);
        rs.iconID = Integer.parseInt(c.getString(2));
        rs.information = c.getString(3);
        rs.tienmuctieu = Long.parseLong(c.getString(4));
        rs.tienhienco = Long.parseLong(c.getString(5));
        rs.ngaykethuc = c.getString(6);

        close();
        return rs;
    }

    public boolean deleteKHTK(int idTK) {
        connect();
        try {
            db.delete(TABLE_KHTietKiem, KHTK_ID + "=" + idTK, null);
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean deleteAll() {
        connect();
        try {
            db.delete(TABLE_KHTietKiem, null, null);
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean updateKHTK(int ID, KHTietKiemItem newItemUpdate) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            cv.put(KHTK_ID, ID);
            cv.put(KHTK_Ten, newItemUpdate.title);
            cv.put(KHTK_iconID, newItemUpdate.iconID);
            cv.put(KHTK_ThongTin, newItemUpdate.information);
            cv.put(KHTK_TienMucTieu, newItemUpdate.tienmuctieu);
            cv.put(KHTK_TienDaTK, newItemUpdate.tienhienco);
            cv.put(KHTK_NgayKetThuc, newItemUpdate.ngaykethuc);

            db.update(TABLE_KHTietKiem, cv, KHTK_ID + "=?", new String[] { String.valueOf(ID)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }
}
