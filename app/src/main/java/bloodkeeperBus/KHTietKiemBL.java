package bloodkeeperBus;

import android.content.Context;

import com.duyhai.bloodkeeper.KeHoachSuKienDetail;
import com.duyhai.bloodkeeper.KeHoachTietKiemDetail;

import java.util.ArrayList;
import java.util.List;

import DAO.KHTietKiemDAO;
import DTO.KHTietKiemItem;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class KHTietKiemBL {

    private KHTietKiemDAO dbTK;

    public KHTietKiemBL(Context context) {
        dbTK = new KHTietKiemDAO(context);
    }

    public List<KHTietKiemItem> getAll() {
        return dbTK.getAllKHTK();
    }

    public KHTietKiemItem getTK(int idTk) {
        return dbTK.getKHTK(idTk);
    }

    public boolean themSK(KeHoachTietKiemDetail tk) {
        KHTietKiemItem item = new KHTietKiemItem(tk);
        item.ID = dbTK.getMaxID() + 1;
        return dbTK.addKHTietKiem(item);
    }

    public boolean deleteSK(int idTK) {
        if(idTK == -1) return dbTK.deleteAll();
        return dbTK.deleteKHTK(idTK);
    }

    public boolean updateSK(int idTk, KHTietKiemItem newItemUpdate) {
        return dbTK.updateKHTK(idTk, newItemUpdate);
    }


}

