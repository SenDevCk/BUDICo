package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.GrivanceUserWiseData;

public interface GrivanceRoleWiseBinder {
    public  void rolegrivanceLoaded(ArrayList<GrivanceUserWiseData> grivanceUserWiseData) ;
    public void grivanceNotFound();


}
