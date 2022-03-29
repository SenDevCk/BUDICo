package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;


import buidcopgrs.in.buidco.entity.GrivanceTypeData;

public interface GrivanceBinder {
    void grivanceLoaded(ArrayList<GrivanceTypeData> grivanceTypeData) ;
    void griTypeNotFound();

}
