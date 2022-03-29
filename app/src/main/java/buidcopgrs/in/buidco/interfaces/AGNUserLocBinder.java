package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.AGNUserByLocData;

public interface AGNUserLocBinder {
    public  void bindAGNUserLoc(ArrayList<AGNUserByLocData> agnuserByLocData) ;

    void noDataFound();
}
