package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.Ward;

public interface WardBinder {
    void bindWard(ArrayList<Ward> wards);
    void cancleWardBinding();
}
