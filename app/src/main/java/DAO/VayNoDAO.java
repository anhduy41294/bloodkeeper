package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import DTO.VayNoItem;

/**
 * Created by HaiTuan on 6/23/2015.
 */
public class VayNoDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_sono = "SONO";
    private static final String SN_ID = "ID";
    private static final String SN_Ten = "Ten";
    private static final String SN_TienNo = "TienNo";
    private static final String SN_TienDaTra = "TienDaTra";
    private static final String SN_Icon = "idIcon";
    private static final String SN_Type = "Loai";
    private static final String SN_GhiChu = "GhiChu";
    private static final String SN_NgayNo = "NgayNo";
    private static final String SN_NgayTra = "NgayTra";

    public VayNoDAO(Context context) {
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
        String sql = "select MAX(ID) from " + TABLE_sono;
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

    public boolean addSoNo(VayNoItem item) {
        connect();
        ContentValues cv = new ContentValues();
        cv.put(SN_ID, item.ID);
        cv.put(SN_Ten, item.ten);
        cv.put(SN_TienNo, item.SoTienNo);
        cv.put(SN_TienDaTra, item.SoTienDaTra);
        cv.put(SN_Icon, item.idIcon);
        if(item.Type) { cv.put(SN_Type, 1); }
        else { cv.put(SN_Type, 0); }
        cv.put(SN_GhiChu, item.ThongTin);
        cv.put(SN_NgayNo, item.NgayNo);
        cv.put(SN_NgayTra, item.NgayTra);

        long index = db.insert(TABLE_sono, null, cv);
        close();
        if (index == -1 ) { return false; }
        else return true;
    }

    public List<VayNoItem> getAllSoNo() {
        connect();
        String[] columns = new String[] {SN_ID, SN_Ten, SN_TienNo, SN_TienDaTra, SN_Icon, SN_Type, SN_GhiChu, SN_NgayNo, SN_NgayTra};
        Cursor c = db.query(TABLE_sono, columns, null, null, null, null, null);

        int cID = c.getColumnIndex(SN_ID);
        int cten = c.getColumnIndex(SN_Ten);
        int ctien = c.getColumnIndex(SN_TienNo);
        int ctiendatra = c.getColumnIndex(SN_TienDaTra);
        int cIcon = c.getColumnIndex(SN_Icon);
        int cType = c.getColumnIndex(SN_Type);
        int cGhiChu = c.getColumnIndex(SN_GhiChu);
        int cNgayno = c.getColumnIndex(SN_NgayNo);
        int cNgaytra = c.getColumnIndex(SN_NgayTra);

        List<VayNoItem> lstItem = new ArrayList<VayNoItem>();
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(cID));
            String Ten = c.getString(cten);
            long TienNo = Long.parseLong(c.getString(ctien));
            long TienDaTra = Long.parseLong(c.getString(ctiendatra));
            int idIcon = Integer.parseInt(c.getString(cIcon));
            int Type = Integer.parseInt(c.getString(cType));
            boolean typeGD;
            if (Type == 0) { typeGD = false; }
            else { typeGD = true; }
            String GhiChu = c.getString(cGhiChu);
            String NgayNo = c.getString(cNgayno);
            String NgayTra = c.getString(cNgaytra);

            VayNoItem item = new VayNoItem(Ten, TienNo, TienDaTra, idIcon, GhiChu, typeGD, NgayNo, NgayTra);
            item.ID = ID;
            lstItem.add(item);
        }
        c.close();
        close();
        return lstItem;
    }

    public boolean deleteSoNo(int id) {
        connect();
        try {
            db.delete(TABLE_sono, SN_ID + "=" + id, null);
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
            db.delete(TABLE_sono, null, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean updateSoNo(int idSN, VayNoItem newItemUpdate) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            cv.put(SN_ID, idSN);
            cv.put(SN_Ten, newItemUpdate.ten);
            cv.put(SN_TienNo, newItemUpdate.SoTienNo);
            cv.put(SN_TienDaTra, newItemUpdate.SoTienDaTra);
            cv.put(SN_Icon, newItemUpdate.idIcon);
            if(newItemUpdate.Type) { cv.put(SN_Type, 1); }
            else { cv.put(SN_Type, 0); }
            cv.put(SN_GhiChu, newItemUpdate.ThongTin);
            cv.put(SN_NgayNo, newItemUpdate.NgayNo);
            cv.put(SN_NgayTra, newItemUpdate.NgayTra);

            db.update(TABLE_sono, cv, SN_ID + "=?", new String[] { String.valueOf(idSN)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }
}
