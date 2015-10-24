package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by HaiTuan on 6/24/2015.
 */
public class DanhMucDAO {
    private static SQLiteDatabase db;
    private DatabaseLoad dl;

    private static final String TABLE_bk = "THAMSO";
    private static final String BK_ID = "ID";
    private static final String BK_TongTien = "TongTien"; //1
    private static final String BK_NEC = "NEC"; //2
    private static final String BK_LTSS = "LTSS"; //3
    private static final String BK_EDU = "EDU"; //4
    private static final String BK_FFA = "FFA"; //5
    private static final String BK_PLAY = "PLAY"; //6
    private static final String BK_GIVE = "GIVE"; //7

    public static int GiaTri = 0;

    public DanhMucDAO(Context context) {
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
        String sql = "select MAX(ID) from " + TABLE_bk;
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

    public boolean addGiaTri(int tongtien, int[] lsttienHu) {
        try{
            connect();
            ContentValues cv = new ContentValues();
            cv.put(BK_ID, 1);
            cv.put(BK_TongTien, tongtien);
            cv.put(BK_NEC, lsttienHu[0]);
            cv.put(BK_LTSS, lsttienHu[1]);
            cv.put(BK_EDU, lsttienHu[2]);
            cv.put(BK_FFA, lsttienHu[3]);
            cv.put(BK_PLAY, lsttienHu[4]);
            cv.put(BK_GIVE, lsttienHu[5]);

            long index = db.insert(TABLE_bk, null, cv);
            close();
            if (index == -1 ) { return false; }
            else return true;
        } catch (Exception ex) { return false; }
    }

    public int getGiaTri(int idCot) {
        int rs = -1;
        connect();
        String[] columns = new String[] {BK_ID, BK_TongTien, BK_NEC, BK_LTSS, BK_EDU, BK_FFA, BK_PLAY, BK_GIVE};
        Cursor c = db.query(TABLE_bk, columns, BK_ID + "=?", new String[] { String.valueOf(1) }, null, null, null, null);
        int iCot = -1;
        switch(idCot) {
            case 1: iCot = c.getColumnIndex(BK_TongTien); break;
            case 2: iCot = c.getColumnIndex(BK_NEC); break;
            case 3: iCot = c.getColumnIndex(BK_LTSS); break;
            case 4: iCot = c.getColumnIndex(BK_EDU); break;
            case 5: iCot = c.getColumnIndex(BK_FFA); break;
            case 6: iCot = c.getColumnIndex(BK_PLAY); break;
            case 7: iCot = c.getColumnIndex(BK_GIVE); break;
        }

        try {
            for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
                rs = Integer.parseInt(c.getString(iCot));
        }
        catch (Exception ex) { }
        c.close();
        close();
        return rs;
    }

    public boolean deleteAll() {
        connect();
        try {
            db.delete(TABLE_bk, null, null);
        } catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean update(int IDHu, int newResult) {
        connect();
        try {
            ContentValues cv = new ContentValues();
            switch(IDHu) {
                case 1: cv.put(BK_TongTien, newResult); break;
                case 2: cv.put(BK_NEC, newResult); break;
                case 3: cv.put(BK_LTSS, newResult); break;
                case 4: cv.put(BK_EDU, newResult); break;
                case 5: cv.put(BK_FFA, newResult); break;
                case 6: cv.put(BK_PLAY, newResult); break;
                case 7: cv.put(BK_GIVE, newResult); break;
            }
            db.update(TABLE_bk, cv, BK_ID + "=?", new String[] { String.valueOf(1)});
        }
        catch (Exception ex) {
            close();
            return false;
        }
        close();
        return true;
    }

    public boolean GenerateCode()
    {
        int[] lstRatio = new int[6];
        lstRatio[0] = 55;
        lstRatio[1] = 10;
        lstRatio[2] = 10;
        lstRatio[3] = 10;
        lstRatio[4] = 10;
        lstRatio[5] = 5;

        addGiaTri(0, lstRatio);
        return false;
    }
}
