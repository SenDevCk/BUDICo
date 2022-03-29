package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.UserByLocData;

public interface UserLocBinder {
    public  void bindUserLoc(ArrayList<UserByLocData> userByLocData) ;

    void noDataFound();
}
