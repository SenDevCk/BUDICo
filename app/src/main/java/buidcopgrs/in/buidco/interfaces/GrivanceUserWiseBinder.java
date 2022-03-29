package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.GrivanceUserWiseData;

public interface GrivanceUserWiseBinder {
    void usergrivanceLoaded(ArrayList<GrivanceUserWiseData> grivanceUserWiseData) ;

    void grivanceNotFound();
}
