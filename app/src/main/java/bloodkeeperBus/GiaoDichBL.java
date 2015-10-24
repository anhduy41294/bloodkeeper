package bloodkeeperBus;

import android.content.Context;

import com.duyhai.bloodkeeper.GiaoDichDetail;

import java.util.ArrayList;
import java.util.List;

import DAO.GiaoDichDAO;
import DTO.GiaoDichItem;

/**
 * Created by HaiTuan on 6/22/2015.
 */
public class GiaoDichBL {
    GiaoDichDAO dbGD;
    public GiaoDichBL(Context context) {
        dbGD = new GiaoDichDAO(context);
    }

    public List<GiaoDichItem> getAll() {
        return dbGD.getAllGiaoDich();
    }

    public GiaoDichItem getGiaoDich(int id) {
        return dbGD.getGD(id);
    }

    public List<GiaoDichItem> getTheoHu(int idHu, int strategy)
    {
        List<GiaoDichItem> lstGD;
        switch (strategy) {
            default:
            case 0:
                lstGD = dbGD.getGDTheoHu(idHu); break;
            case 1:
                List<GiaoDichItem> lst = dbGD.getGDTheoHu(idHu);
                lstGD = new ArrayList<GiaoDichItem>();
                int size = lst.size();
                for(int i = 0; i < size; ++i)
                    if(!lst.get(i).Type) lst.add(lst.get(i));
                break;
            case 2:
                List<GiaoDichItem> lst2 = dbGD.getGDTheoHu(idHu);
                lstGD = new ArrayList<GiaoDichItem>();
                int size2 = lst2.size();
                for(int i = 0; i < size2; ++i)
                    if(lst2.get(i).Type) lst2.add(lst2.get(i));
                break;

        }
        // strategy = 0 get all
        // strategy = 1 get theo chi
        // strategy = 2 get theo thu
        return lstGD;
    }

    public List<GiaoDichItem> getTheoKeHoach(int idKH)
    {
        return dbGD.getGDTheoKH(idKH);
    }

    public List<GiaoDichItem> getTheoChi()
    {
        List<GiaoDichItem> lstGD = dbGD.getAllGiaoDich();
        List<GiaoDichItem> lstChi = new ArrayList<GiaoDichItem>();
        int size = lstGD.size();
        for(int i = 0; i < size; ++i)
            if(!lstGD.get(i).Type) lstChi.add(lstGD.get(i));
        return lstChi;
    }

    public List<GiaoDichItem> getTheoThu()
    {
        List<GiaoDichItem> lstGD = dbGD.getAllGiaoDich();
        List<GiaoDichItem> lstThu = new ArrayList<GiaoDichItem>();
        int size = lstGD.size();
        for(int i = 0; i < size; ++i)
            if(lstGD.get(i).Type) lstThu.add(lstGD.get(i));
        return lstThu;
    }

    public List<GiaoDichItem> getTheoTheLoai(int idTL) {
        return dbGD.getGDTheoTL(idTL);
    }

    public boolean addGiaoDich(GiaoDichDetail newGD) {
        GiaoDichItem newItem = new GiaoDichItem(newGD);
        newItem.ID = dbGD.getMaxID() + 1;
        return dbGD.addGiaoDich(newItem);
    }

    public boolean delete(int idGD) {
        if (idGD == -1) return dbGD.deleteAll();
        return dbGD.deleteGD(idGD);
    }

    public boolean updateGD(int id, GiaoDichItem itemUpdate) {
        return dbGD.updateGD(id, itemUpdate);
    }


}
