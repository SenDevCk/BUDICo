package buidcopgrs.in.buidco.interfaces;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.GravanceReportData;

public interface GrivanceReportBinder {
    void bindReport(ArrayList<GravanceReportData> gravanceReportDataArrayList);
    void cancleReportBinding();
}
