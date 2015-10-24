package bloodkeeperBus;

import android.content.Context;

import com.duyhai.bloodkeeper.KeHoachSuKienDetail;

import java.util.ArrayList;
import java.util.List;

import DAO.KHSuKienDAO;
import DAO.VayNoDAO;
import DTO.KHSuKienItem;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class KHSuKienBL {
    private KHSuKienDAO dbSK;

    public KHSuKienBL(Context context) {
        dbSK = new KHSuKienDAO(context);
    }

    public List<KHSuKienItem> getAll() {
        return dbSK.getAllKHSK();
    }

    public KHSuKienItem getSK(int idSK) {
        return dbSK.getKHSK(idSK);
    }

    public boolean themSK(KeHoachSuKienDetail sk) {
        KHSuKienItem item = new KHSuKienItem(sk);
        item.ID = dbSK.getMaxID() + 1;
        return dbSK.addKHSuKien(item);
    }

    public boolean deleteSK(int idSK) {
        if(idSK == -1) return dbSK.deleteAll();
        return dbSK.deleteKHSK(idSK);
    }

    public boolean updateSK(int idSk, KHSuKienItem newItemUpdate) {
        return dbSK.updateKHSK(idSk, newItemUpdate);
    }

    public void themTien(int idKH, boolean loai, long sotien) {
        KHSuKienItem item = getSK(idKH);
        if (item.apdung) {
            if(loai) item.tienthu += sotien;
            else item.tienchi += sotien;
        }
        updateSK(idKH, item);
    }

}
