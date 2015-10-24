package bloodkeeperBus;

import android.content.Context;
import java.util.List;

import DAO.HuTienDAO;
import DTO.HuTienItem;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class HuTienBL {

    private List<HuTienItem> lstHT;
    private HuTienDAO dbHT;

    public HuTienBL(Context context) {
        dbHT = new HuTienDAO(context);
        lstHT = dbHT.getAllHuTien();
    }

    public List<HuTienItem> getAllHu() {
        if (lstHT.size() != 0)
            return lstHT;
        else
            return null;
    }

    public HuTienItem getHuTien(int id) {
        return lstHT.get(id);
    }

    public void generate6HuTien(int iconNEC, int iconLTSS, int iconEDU, int iconFFA, int iconPLAY, int iconGIVE) {
        if (dbHT.getAllHuTien().size() == 0) {
            HuTienItem NEC = new HuTienItem("NEC", iconNEC, 0, 0, "Sống là để ăn");
            NEC.ID = 0;
            dbHT.addHuTien(NEC);

            HuTienItem LTSS = new HuTienItem("LTSS", iconLTSS, 0, 0, "Tương lai trong tầm tay");
            LTSS.ID = 1;
            dbHT.addHuTien(LTSS);

            HuTienItem EDU = new HuTienItem("EDU", iconEDU, 0, 0, "Học là không tiếc tiền");
            EDU.ID = 2;
            dbHT.addHuTien(EDU);

            HuTienItem FFA = new HuTienItem("FFA", iconFFA, 0, 0, "Tự do mà xài");
            FFA.ID = 3;
            dbHT.addHuTien(FFA);

            HuTienItem PLAY = new HuTienItem("PLAY", iconPLAY, 0, 0, "YOLO đi mấy chế");
            PLAY.ID = 4;
            dbHT.addHuTien(PLAY);

            HuTienItem GIVE = new HuTienItem("GIVE", iconGIVE, 0, 0, "Lá nát đùm lá tả tơi");
            GIVE.ID = 5;
            dbHT.addHuTien(GIVE);

            lstHT = dbHT.getAllHuTien();
        }

    }

    public boolean updateTienTungHu(int idHu, boolean loai, long tien) {
        HuTienItem newItem = dbHT.getAllHuTien().get(idHu);
        if (loai) newItem.tongThu += tien; // true = thu
        else newItem.tongChi += tien;  // false = chi
        return dbHT.updateHuTien(idHu, newItem);
    }

    public long getThuChitheoHu(int idHu, boolean loai) {
        if (loai) return lstHT.get(idHu).tongThu;
        else return lstHT.get(idHu).tongChi;
    }
}
