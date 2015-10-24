package DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseLoad extends SQLiteOpenHelper {
    public DatabaseLoad(Context context) {
        super(context, DB_Name, null, vs_DB);
    }

    private static final String DB_Name = "BloodKeeper.db";
    private static final int vs_DB = 1;
    private static SQLiteDatabase db;

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

    private static final String TABLE_hutien = "HUTIEN";
    private static final String HT_ID = "ID";
    private static final String HT_Ten = "Ten";
    private static final String HT_iconID = "idIcon";
    private static final String HT_TongThu = "TienThu";
    private static final String HT_TongChi = "TienChi";
    private static final String HT_ThongTin = "ThongTin";

    private static final String TABLE_KHTietKiem = "KHTIETKIEM";
    private static final String KHTK_ID = "ID";
    private static final String KHTK_Ten = "Ten";
    private static final String KHTK_iconID = "idIcon";
    private static final String KHTK_ThongTin = "ThongTin";
    private static final String KHTK_TienMucTieu = "TienMucTieu";
    private static final String KHTK_TienDaTK = "TienDaTK";
    private static final String KHTK_NgayKetThuc = "NgayKetThuc";

    private static final String TABLE_KHSuKien = "KHSUKIEN";
    private static final String KHSK_ID = "ID";
    private static final String KHSK_Ten = "Ten";
    private static final String KHSK_TienThu = "TienThu";
    private static final String KHSK_TienChi = "TienChi";
    private static final String KHSK_iconID = "idIcon";
    private static final String KHSK_ThongTin = "ThongTin";
    private static final String KHSK_ApDung = "ApDung";

    private static final String TABLE_theloai = "THELOAI";
    private static final String TL_ID = "ID";
    private static final String TL_Name = "Ten";
    private static final String TL_Type = "Type";
    private static final String TL_BieuTuong = "Hinh";

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

    private static final String TABLE_bk = "THAMSO";
    private static final String BK_ID = "ID";
    private static final String BK_TongTien = "TongTien";
    private static final String BK_NEC = "NEC";
    private static final String BK_LTSS = "LTSS";
    private static final String BK_EDU = "EDU";
    private static final String BK_FFA = "FFA";
    private static final String BK_PLAY = "PLAY";
    private static final String BK_GIVE = "GIVE";

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL("CREATE TABLE " + TABLE_giaodich + " ("
                + GD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GD_Ten + " TEXT NOT NULL, "
                + GD_Tien + " INTEGER, "
                + GD_NgayGiaoDich + " TEXT, "
                + GD_Type + " INTEGER, "
                + GD_GhiChu + " TEXT, "
                + GD_IDhutien + " INTEGER, "
                + GD_IDTheLoai + " INTEGER, "
                + GD_HinhTheLoai + " INTEGER, "
                + GD_IDKeHoach + " INTEGER);");

        arg0.execSQL("CREATE TABLE " + TABLE_hutien + " ("
                + HT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HT_Ten + " TEXT NOT NULL, "
                + HT_iconID + " INTEGER, "
                + HT_TongThu + " INTEGER, "
                + HT_TongChi + " INTEGER, "
                + HT_ThongTin + " TEXT);");

        arg0.execSQL("CREATE TABLE " + TABLE_KHTietKiem + " ("
                + KHTK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KHTK_Ten + " TEXT NOT NULL, "
                + KHTK_iconID + " INTEGER, "
                + KHTK_ThongTin + " TEXT, "
                + KHTK_TienMucTieu + " INTEGER, "
                + KHTK_TienDaTK + " INTEGER, "
                + KHTK_NgayKetThuc + " TEXT);");

        arg0.execSQL("CREATE TABLE " + TABLE_KHSuKien + " ("
                + KHSK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KHSK_Ten + " TEXT NOT NULL, "
                + KHSK_TienThu + " INTEGER, "
                + KHSK_TienChi + " INTEGER, "
                + KHSK_iconID + " INTEGER, "
                + KHSK_ThongTin + " TEXT, "
                + KHSK_ApDung + " INTEGER);");

        arg0.execSQL("CREATE TABLE " + TABLE_theloai + " ("
                + TL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TL_Name + " TEXT NOT NULL, "
                + TL_Type + " INTEGER, "
                + TL_BieuTuong + " INTEGER);");

        arg0.execSQL("CREATE TABLE " + TABLE_sono + " ("
                + SN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SN_Ten + " TEXT NOT NULL, "
                + SN_TienNo + " INTEGER, "
                + SN_TienDaTra + " INTEGER, "
                + SN_Icon + " INTEGER, "
                + SN_Type + " INTEGER, "
                + SN_GhiChu + " TEXT, "
                + SN_NgayNo + " TEXT, "
                + SN_NgayTra + " TEXT);");

        arg0.execSQL("CREATE TABLE " + TABLE_bk + " ("
                + BK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BK_TongTien + " INTEGER, "
                + BK_NEC + " INTEGER, "
                + BK_LTSS + " INTEGER, "
                + BK_EDU + " INTEGER, "
                + BK_FFA + " INTEGER, "
                + BK_PLAY + " INTEGER, "
                + BK_GIVE + " INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_giaodich);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_KHTietKiem);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_KHSuKien);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_sono);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_hutien);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_theloai);
        arg0.execSQL("DROP TABLE IF EXISTS " + TABLE_bk);
        onCreate(db);
    }
}
