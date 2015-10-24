package bloodkeeperBus;

import android.content.Context;

import java.util.List;

import DAO.TheLoaiDAO;
import DTO.TheLoaiItem;

/**
 * Created by HaiTuan on 6/24/2015.
 */
public class TheLoaiBL {
    private TheLoaiDAO dbTL;
    public TheLoaiBL(Context context) {
        dbTL = new TheLoaiDAO(context);
    }

    public List<TheLoaiItem> getAll() {
        return dbTL.getAllTheLoai();
    }

    public boolean addTheLoai(TheLoaiItem item) {
        item.ID = dbTL.getMaxID() + 1;
        return dbTL.addTheLoai(item);
    }

    public boolean delete(int idTL) {
        if(idTL == -1) return dbTL.deleteAll();
        return dbTL.deleteTL(idTL);
    }

    public boolean update(int idTl, TheLoaiItem newItemUpdate) {
        return dbTL.updateTL(idTl, newItemUpdate);
    }

    public void generateTheLoai(int iconGD, int iconTY, int iconHT, int iconAU, int iconGM) {
        if(dbTL.getAllTheLoai().size() == 0) {
            TheLoaiItem GD = new TheLoaiItem("Gia đình", false, iconGD, 0);
            GD.ID = 0;
            dbTL.addTheLoai(GD);
            TheLoaiItem TY = new TheLoaiItem("Tình yêu", false, iconTY, 1);
            TY.ID = 1;
            dbTL.addTheLoai(TY);
            TheLoaiItem HT = new TheLoaiItem("Học tập", false, iconHT, 2);
            HT.ID = 2;
            dbTL.addTheLoai(HT);
            TheLoaiItem AU = new TheLoaiItem("Ăn uống", false, iconAU, 3);
            AU.ID = 3;
            dbTL.addTheLoai(AU);
            TheLoaiItem GM = new TheLoaiItem("Game", false, iconGM, 4);
            GM.ID = 4;
            dbTL.addTheLoai(GM);
        }
    }
}
