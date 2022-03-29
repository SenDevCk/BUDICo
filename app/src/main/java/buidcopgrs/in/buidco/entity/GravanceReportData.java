package buidcopgrs.in.buidco.entity;


import org.ksoap2.serialization.SoapObject;

public class GravanceReportData {
    private String _GrievanceType="";
    private String _Total="";
    private String _Rejected="";
    private String _Resolved="";
    private String _Pending="";

    private static Class<GravanceReportData> GIVANCE_REPORT_CLASS= GravanceReportData.class;


    public GravanceReportData(SoapObject sobj)
    {

        this.set_GrievanceType(sobj.getProperty("_GrievanceType").toString());
        this.set_Total(sobj.getProperty("_Total").toString());
        this.set_Rejected(sobj.getProperty("_Rejected").toString());
        this.set_Resolved(sobj.getProperty("_Resolved").toString());
        this.set_Pending(sobj.getProperty("_Pending").toString());
    }
    public GravanceReportData() {
        super();
    }

    public static Class<GravanceReportData> getBlockData_CLASS() {
        return GIVANCE_REPORT_CLASS;
    }

    public static void setBlockData_CLASS(Class<GravanceReportData> blockData_CLASS) {
        GIVANCE_REPORT_CLASS = blockData_CLASS;
    }


    public String get_GrievanceType() {
        return _GrievanceType;
    }

    public void set_GrievanceType(String _GrievanceType) {
        this._GrievanceType = _GrievanceType;
    }

    public String get_Total() {
        return _Total;
    }

    public void set_Total(String _Total) {
        this._Total = _Total;
    }

    public String get_Rejected() {
        return _Rejected;
    }

    public void set_Rejected(String _Rejected) {
        this._Rejected = _Rejected;
    }

    public String get_Resolved() {
        return _Resolved;
    }

    public void set_Resolved(String _Resolved) {
        this._Resolved = _Resolved;
    }

    public String get_Pending() {
        return _Pending;
    }

    public void set_Pending(String _Pending) {
        this._Pending = _Pending;
    }
}
