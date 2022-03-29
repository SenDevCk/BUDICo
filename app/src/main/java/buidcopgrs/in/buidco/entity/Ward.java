package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.SoapObject;

public class Ward {

    String wardCode="";
    String wardname="";
    String _CCode="";

    public static Class<Ward> warddata= Ward.class;
    public Ward(SoapObject sobj)
    {

        this.setWardname(sobj.getProperty("_Ward_Name").toString());

        this.setWardCode(sobj.getProperty("_Ward_Code").toString());

        this.set_CCode(sobj.getProperty("_CCode").toString());
    }
    public Ward() {
        super();
    }
    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String _wardCode) {
        this.wardCode = _wardCode;
    }

    public String getWardname() {
        return wardname;
    }

    public void setWardname(String _wardname) {
        this.wardname = _wardname;
    }

    public String get_CCode() {
        return _CCode;
    }

    public void set_CCode(String panchayatCode) {
        _CCode = panchayatCode;
    }
}
