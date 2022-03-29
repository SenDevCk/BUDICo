package buidcopgrs.in.buidco.entity;


import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class GrivanceUserWiseData implements Serializable {

    private String _GrievanceId = "";
    private String _ForwardTo = "";
    private String _GrievanceType = "";
    private String _Description = "";
    private String _LandMark = "";
    private String _UserName = "";
    private String _EntryDate = "";
    private String _Status = "";
    private String _StatusCode = "";
    private String photoByte1 = "";


    public static Class<GrivanceUserWiseData> GRIVANCE_User_Wise_CLASS = GrivanceUserWiseData.class;


    public GrivanceUserWiseData(SoapObject sobj, String role) {
        this.set_GrievanceId(sobj.getProperty("_GrievanceId").toString());
        if (role.equalsIgnoreCase("AGN")) {
            this.set_ForwardTo(sobj.getProperty("_ForwardTo").toString());
        } else {
            this.set_ForwardTo("");
        }
        this.set_GrievanceType(sobj.getProperty("_GrievanceType").toString());
        this.set_Description(sobj.getProperty("_Description").toString());
        this.set_LandMark(sobj.getProperty("_LandMark").toString());
        //this.set_UserName(sobj.getProperty("_UserName").toString());
        this.set_EntryDate(sobj.getProperty("_EntryDate").toString());
        this.set_Status(sobj.getProperty("_Status").toString());
        this.set_StatusCode(sobj.getProperty("_StatusCode").toString());
        this.setPhotoByte1(sobj.getProperty("_PhotoPath").toString());


    }

    public GrivanceUserWiseData() {
        super();
    }

    public String get_GrievanceId() {
        return _GrievanceId;
    }

    public void set_GrievanceId(String _GrievanceId) {
        this._GrievanceId = _GrievanceId;
    }

    public String get_GrievanceType() {
        return _GrievanceType;
    }

    public void set_GrievanceType(String _GrievanceType) {
        this._GrievanceType = _GrievanceType;
    }

    public String get_Description() {
        return _Description;
    }

    public void set_Description(String _Description) {
        this._Description = _Description;
    }

    public String get_LandMark() {
        return _LandMark;
    }

    public void set_LandMark(String _LandMark) {
        this._LandMark = _LandMark;
    }

    public String get_UserName() {
        return _UserName;
    }

    public void set_UserName(String _UserName) {
        this._UserName = _UserName;
    }

    public String get_EntryDate() {
        return _EntryDate;
    }

    public void set_EntryDate(String _EntryDate) {
        this._EntryDate = _EntryDate;
    }

    public String get_Status() {
        return _Status;
    }

    public void set_Status(String _Status) {
        this._Status = _Status;
    }

    public String get_StatusCode() {
        return _StatusCode;
    }

    public void set_StatusCode(String _StatusCode) {
        this._StatusCode = _StatusCode;
    }

    public String getPhotoByte1() {
        return photoByte1;
    }

    public void setPhotoByte1(String photoByte1) {
        this.photoByte1 = photoByte1;
    }

    public String get_ForwardTo() {
        return _ForwardTo;
    }

    public void set_ForwardTo(String _ForwardTo) {
        this._ForwardTo = _ForwardTo;
    }
}
