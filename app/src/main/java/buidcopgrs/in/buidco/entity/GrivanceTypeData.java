package buidcopgrs.in.buidco.entity;


import org.ksoap2.serialization.SoapObject;

public class GrivanceTypeData {
    private String Code="";
    private String Detail="";
    public static Class<GrivanceTypeData> GRIVANCE_TYPE_CLASS= GrivanceTypeData.class;


    public GrivanceTypeData(SoapObject sobj)
    {

        this.setCode(sobj.getProperty("_Code").toString());
        this.setDetail(sobj.getProperty("_Detail").toString());
    }
    public GrivanceTypeData() {
        super();
    }


    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
