package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.DistrictData;

public interface DistrictBinder {
     void distLoaded(ArrayList<DistrictData> districtData) ;
     void distNotFound();

}
