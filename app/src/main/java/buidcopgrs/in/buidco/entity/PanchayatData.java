package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.SoapObject;

public class PanchayatData {


    String Pcode="";
    String Pname="";
    String Pnamehn="";
    String BlockCode="";

    public static Class<PanchayatData> PanchayatData_CLASS=PanchayatData.class;


    public PanchayatData(SoapObject sobj)
    {

        this.Pcode=sobj.getProperty(0).toString();
        this.Pname=sobj.getProperty(1).toString();

    }
    public PanchayatData() {
        super();
    }

    public String getPcode() {
        return Pcode;
    }

    public void setPcode(String _pcode) {
        Pcode = _pcode;
    }

    public String getPname() {
        return Pname;
    }

    public void setPname(String _pname) {
        Pname = _pname;
    }

    public String getPnamehn() {
        return Pnamehn;
    }

    public void setPnamehn(String pnamehn) {
        Pnamehn = pnamehn;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }
}
