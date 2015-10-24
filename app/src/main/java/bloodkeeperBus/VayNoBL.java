package bloodkeeperBus;

import android.content.Context;

import com.duyhai.bloodkeeper.VayNoDetail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import DAO.VayNoDAO;
import DTO.VayNoItem;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class VayNoBL {

    private VayNoDAO dbVN;

    public VayNoBL(Context context) {
        dbVN = new VayNoDAO(context);

    }

    public VayNoItem getNo(int id) {
        List<VayNoItem> lstVN = dbVN.getAllSoNo();
        for(int i = 0; i < lstVN.size(); ++i)
            if(lstVN.get(i).ID == id)
                return lstVN.get(i);
        return null;
    }

    public List<VayNoItem> getAll() {
        return dbVN.getAllSoNo();
    }

    public List<VayNoItem> getAllChoVay() {
        List<VayNoItem> lstVN = dbVN.getAllSoNo();
        List<VayNoItem> lst = new ArrayList<VayNoItem>();
        int size = lstVN.size();
        for (int i = 0; i < size; ++i)
        {
            if(lstVN.get(i).Type) {
                VayNoItem item = lstVN.get(i);
                lst.add(item);
            }
        }
        return lst;
    }

    public List<VayNoItem> getAllMuonNo() {
        List<VayNoItem> lstVN = dbVN.getAllSoNo();
        List<VayNoItem> lst = new ArrayList<VayNoItem>();
        int size = lstVN.size();
        for (int i = 0; i < size; ++i)
        {
            if(!lstVN.get(i).Type) {
                VayNoItem item = lstVN.get(i);
                lst.add(item);
            }
        }
        return lst;
    }

    public boolean themSoNo(VayNoItem item) {
        item.ID = dbVN.getMaxID() + 1;
        return dbVN.addSoNo(item);
    }

    public boolean updateSoNo(int idNo, VayNoItem itemUpdate) {
        return dbVN.updateSoNo(idNo, itemUpdate);
    }

    public boolean deleteSoNo(int idNo) {
        if(idNo == -1) return dbVN.deleteAll();
        return dbVN.deleteSoNo(idNo);
    }
}
